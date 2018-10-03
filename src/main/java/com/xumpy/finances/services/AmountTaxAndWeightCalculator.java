package com.xumpy.finances.services;

import com.xumpy.finances.model.AccountingModel;
import com.xumpy.finances.model.AmountAndTax;
import com.xumpy.government.domain.GovernmentCost;
import com.xumpy.government.domain.GovernmentCostType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class AmountTaxAndWeightCalculator {
    public static BigDecimal amountExclusiefTax(BigDecimal amount, BigDecimal tax){
        BigDecimal internAmount = amount == null ? new BigDecimal(0) : amount;
        BigDecimal internTax = tax == null ? new BigDecimal(0) : tax;

        return internAmount.divide(internTax.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP).add(new BigDecimal(1)), 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
    }

    public static BigDecimal amountTax(BigDecimal amount, BigDecimal tax){
        BigDecimal internAmount = amount == null ? new BigDecimal(0) : amount;
        BigDecimal internTax = tax == null ? new BigDecimal(0) : tax;

        return internAmount.subtract(amountExclusiefTax(amount, internTax)).setScale(4, RoundingMode.HALF_UP);
    }

    public static BigDecimal applyWeight(BigDecimal amount, BigDecimal weight){
        BigDecimal internAmount = amount == null ? new BigDecimal(0) : amount;
        BigDecimal internWeight = weight == null ? new BigDecimal(0) : weight;

        return internAmount.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP).multiply(internWeight).setScale(4, RoundingMode.HALF_UP);
    }

    private static Map<Integer, List<GovernmentCost>> governmentCostPerLevel(Iterable<? extends GovernmentCost> governmentCosts){
        Map<Integer, List<GovernmentCost>> governmentCostPerLevel = new TreeMap<>();

        for(GovernmentCost governmentCost: governmentCosts){
            List<GovernmentCost> governmentCostList;
            if (governmentCostPerLevel.containsKey(governmentCost.getGovernmentCostType().getLevel())){
                governmentCostList = governmentCostPerLevel.get(governmentCost.getGovernmentCostType().getLevel());
            } else {
                governmentCostList = new ArrayList<>();
            }
            governmentCostList.add(governmentCost);
            governmentCostPerLevel.put(governmentCost.getGovernmentCostType().getLevel(), governmentCostList);
        }

        return governmentCostPerLevel;
    }

    private static List<AmountAndTax> sortGovernmentCosts(List<GovernmentCost> governmentCosts){
        List<AmountAndTax> sortedGovernmentCosts = new LinkedList<>();
        for (GovernmentCost governmentCost: governmentCosts){
            sortedGovernmentCosts.add(new AmountAndTax(governmentCost.getStartAmount(), governmentCost.getTaxPercentage()));
        }
        Collections.sort(sortedGovernmentCosts);

        return sortedGovernmentCosts;
    }

    private static AmountAndTax getNextValueIfExist(List<AmountAndTax> sortedGovernmentCosts, int currentPosition){
        if (sortedGovernmentCosts.size() == currentPosition + 1){
            return null;
        }
        return sortedGovernmentCosts.get(currentPosition + 1);
    }

    private static List<Map<String, AmountAndTax>> createBoundaries(List<GovernmentCost> governmentCosts){
        List<AmountAndTax> sortedGovernmentCosts = sortGovernmentCosts(governmentCosts);
        List<Map<String, AmountAndTax>> boundaries = new ArrayList<>();
        for(int i=0; i<sortedGovernmentCosts.size(); i++){
            AmountAndTax currentAmountAndTax = sortedGovernmentCosts.get(i);
            Map<String, AmountAndTax> boundary = new HashMap<>();

            AmountAndTax fromBoundary;
            AmountAndTax toBoundary;
            if (sortedGovernmentCosts.get(i).getAmount().compareTo(new BigDecimal(0)) == 0){
                fromBoundary = currentAmountAndTax;
                toBoundary = getNextValueIfExist(sortedGovernmentCosts, i);
            } else {
                if (boundaries.size() == 0){
                    fromBoundary = new AmountAndTax(new BigDecimal(0), new BigDecimal(0));
                    toBoundary = sortedGovernmentCosts.get(i);
                    i--;
                } else {
                    fromBoundary = sortedGovernmentCosts.get(i);
                    toBoundary = getNextValueIfExist(sortedGovernmentCosts, i);
                }
            }
            boundary.put("FROM", fromBoundary);
            boundary.put("TO", toBoundary);
            boundaries.add(boundary);
        }
        return boundaries;
    }

    private static BigDecimal calculateCost(BigDecimal amount, Map<String, AmountAndTax> boundary){
        BigDecimal result;

        AmountAndTax from = boundary.get("FROM");
        AmountAndTax to = boundary.get("TO");

        if (to != null && amount.compareTo(to.getAmount()) > 0){
            result = to.getAmount().subtract(from.getAmount()).divide(new BigDecimal(100)).multiply(from.getTax());
        } else {
            if (amount.compareTo(from.getAmount()) < 0){
                result = new BigDecimal(0);
            } else {
                result =  amount.subtract(from.getAmount()).divide(new BigDecimal(100)).multiply(from.getTax());
            }
        }
        return result;
    }

    public static Map<String, BigDecimal> calculateGovernmentCosts(AccountingModel accountingModel, Iterable<? extends GovernmentCost> governmentCosts){
        final String BTW = "BTW";
        Map<String, BigDecimal> calcGovernmentCosts = new HashMap<>();
        calcGovernmentCosts.put(BTW, accountingModel.getRevenue().getBedragWeightTax().subtract(accountingModel.getCosts().getBedragWeightTax()));

        Map<Integer, List<GovernmentCost>> governmentCostPerLevel = governmentCostPerLevel(governmentCosts);

        BigDecimal totalGovernmentCosts = new BigDecimal(0);
        for (Map.Entry<Integer, List<GovernmentCost>> governmentCostPerLevelEntry: governmentCostPerLevel.entrySet()){
            String levelType = governmentCostPerLevelEntry.getValue().get(0).getGovernmentCostType().getType();
            BigDecimal amount = new BigDecimal(0);
            List<Map<String, AmountAndTax>> boundaries = createBoundaries(governmentCostPerLevelEntry.getValue());
            for(Map<String, AmountAndTax> boundary: boundaries){
                amount = amount.add(calculateCost(accountingModel.getRevenue().getBedragWeightExclusiveTax().subtract(accountingModel.getCosts().getBedragWeightExclusiveTax().add(totalGovernmentCosts)), boundary));
            }
            totalGovernmentCosts = totalGovernmentCosts.add(amount);
            calcGovernmentCosts.put(levelType, amount);
        }

        return calcGovernmentCosts;
    }
}
