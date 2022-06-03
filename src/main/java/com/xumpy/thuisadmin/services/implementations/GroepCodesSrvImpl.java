package com.xumpy.thuisadmin.services.implementations;

import com.xumpy.thuisadmin.dao.implementations.GroepCodesDaoImpl;
import com.xumpy.thuisadmin.dao.model.GroepCodesDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.domain.Groepen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroepCodesSrvImpl {
    @Autowired GroepCodesDaoImpl groepCodesDao;

    public Boolean groepContainsInterRekening(Groepen groep){
        List<GroepCodesDaoPojo> groepCodesDaoPojos = groepCodesDao.findAllByGroep(groep.getPk_id());
        for (GroepCodesDaoPojo groepCodesDaoPojo: groepCodesDaoPojos){
            if (groepCodesDaoPojo.getCodeId().equals("INTER_REKENING")){
                return true;
            }
        }
        return false;
    }

    public List<String> getCodesByGroup(Groepen groep){
        List<GroepCodesDaoPojo> groepCodesDaoPojos = groepCodesDao.findAllByGroep(groep.getPk_id());
        List<String> codes = new ArrayList<>();

        for (GroepCodesDaoPojo groepCodesDaoPojo: groepCodesDaoPojos){
            codes.add(groepCodesDaoPojo.getCodeId());
        }
        return codes;
    }

    private GroepCodesDaoPojo findGroepCode(List<GroepCodesDaoPojo> existingGroepCodesDaoPojos, String codeId){
        for (GroepCodesDaoPojo groepCodesDaoPojo: existingGroepCodesDaoPojos){
            if (groepCodesDaoPojo.getCodeId().equals(codeId)){
                return groepCodesDaoPojo;
            }
        }
        return null;
    }

    public void saveCodesInGroup(Groepen groep, List<String> codeIds){
        List<GroepCodesDaoPojo> existingGroepCodesDaoPojos = groepCodesDao.findAllByGroep(groep.getPk_id());

        for(String codeId: codeIds){
            GroepCodesDaoPojo groepCode = null;
            for (GroepCodesDaoPojo groepCodesDaoPojo: existingGroepCodesDaoPojos){
                if (groepCodesDaoPojo.getCodeId().equals(codeId)){
                    groepCode = groepCodesDaoPojo;
                }
            }
            if (groepCode == null){
                groepCode = new GroepCodesDaoPojo();
                groepCode.setGroep(new GroepenDaoPojo(groep));
                groepCode.setCodeId(codeId);

                groepCodesDao.save(groepCode);
            }
        }
        for (GroepCodesDaoPojo groepCodesDaoPojo: existingGroepCodesDaoPojos){
            Boolean found = false;
            for(String codeId: codeIds){
                if(groepCodesDaoPojo.getCodeId().equals(codeId)){
                    found = true;
                }
            }
            if (!found){
                groepCodesDao.delete(groepCodesDaoPojo);
            }
        }
    }

    public List<GroepCodesDaoPojo> getGroepCodesByYearAndHoofdGroepId(Integer year, Integer hoofdGroepId){
        return groepCodesDao.findAllByYearAndHoofdCodeId(year, hoofdGroepId);
    }
}
