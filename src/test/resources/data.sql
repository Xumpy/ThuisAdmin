delete from ta_jobs;
delete from ta_job_groups;
delete from ta_company;
delete from ta_bedrag_documenten;
delete from ta_bedragen;
delete from ta_type_groep;
delete from ta_rekeningen;
delete from ta_personen;

insert into ta_personen(pkId, naam, voornaam, user_name, md5_password) values(1, 'Test Persoon 1', 'martens', 'nico', 'e10adc3949ba59abbe56e057f20f883e');
insert into ta_personen(pkId, naam, voornaam, user_name, md5_password) values(2, 'Test Persoon 2', 'test', 'test2', 'e10adc3949ba59abbe56e057f20f883e');

insert into ta_rekeningen(pkId, fk_personen_id, waarde, naam, laatst_bijgewerkt) values (1, 1, 2000, 'Test Rekening 1', DATE'2015-02-18');
insert into ta_rekeningen(pkId, fk_personen_id, waarde, naam, laatst_bijgewerkt) values (2, 1, 250, 'Test Rekening 2', DATE'2015-02-18');
insert into ta_rekeningen(pkId, fk_personen_id, waarde, naam, laatst_bijgewerkt) values (3, 2, 2000, 'Test Rekening 1', DATE'2015-02-18');

insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(1, null, 1, 'Hoofdgroep', null, 0, null, 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(4, null, 1, 'Inter Rekening', null, 0, 'INTER_REKENING', 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(7, null, 1, 'Hoofdgroep 2', null, 0, null, 0);

insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(2, 1, 1, 'Test', null, 1, null, 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(3, 1, 1, 'Test Positief', null, 0, null, 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(5, 4, 1, 'Afhalen', null, 1, 'INTER_REKENING', 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(6, 4, 1, 'Bijzetten', null, 0, 'INTER_REKENING', 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(8, 7, 1, 'Hoofdgroep 2 Test', null, 1, null, 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(9, 7, 1, 'Hoofdgroep 2 Test Positief', null, 0, null, 0);
insert into ta_type_groep(pkId, fk_hoofd_type_groep_id, fk_personen_id, naam, omschrijving, negatief, code_id, public_groep)
  values(10, 7, 2, 'Hoofdgroep Public', null, 0, null, 1);

insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(1, 2, 1, 1, 200, DATE'2015-02-18', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(2, 2, 1, 1, 100, DATE'2015-02-19', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(3, 2, 1, 1, 50, DATE'2015-02-19', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(4, 3, 1, 1, 2000, DATE'2015-02-20', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(5, 2, 1, 1, 50, DATE'2015-02-21', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(6, 2, 1, 1, 60, DATE'2015-02-21', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(7, 2, 1, 1, 70, DATE'2015-02-22', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(8, 2, 1, 1, 40, DATE'2015-02-23', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(9, 2, 1, 1, 20, DATE'2015-02-24', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(10, 2, 1, 1, 30, DATE'2015-02-24', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(11, 2, 1, 1, 20, DATE'2015-02-24', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(12, 2, 1, 1, 10, DATE'2015-02-25', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(13, 2, 1, 2, 50, DATE'2015-02-19', 'test rekening 2');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(14, 2, 1, 2, 100, DATE'2015-02-20', 'test rekening 2');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(15, 2, 1, 2, 40, DATE'2015-02-21', 'test rekening 2');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(16, 2, 1, 2, 180, DATE'2015-02-22', 'test rekening 2');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(17, 4, 1, 1, 20, DATE'2015-02-01', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(18, 5, 1, 1, 30, DATE'2015-02-02', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(19, 5, 1, 1, 20, DATE'2015-02-02', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(20, 5, 2, 3, 10, DATE'2015-02-02', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(21, 2, 2, 3, 20, DATE'2015-02-24', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(22, 2, 2, 3, 10, DATE'2015-02-25', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(23, 2, 2, 3, 50, DATE'2015-02-19', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(24, 2, 2, 3, 100, DATE'2015-02-20', 'nog een test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(25, 2, 2, 3, 40, DATE'2015-02-21', 'test');
insert into ta_bedragen(pkId, fk_type_groep_id, fk_persoon_id, fk_rekening_id, bedrag, datum, omschrijving)
  values(26, 10, 2, 1, 200, DATE'2015-02-23', 'test');

insert into ta_bedrag_documenten(pkId, fk_bedrag_id, omschrijving, document, document_naam, document_mime)
  values(1, 1, 'PC madness', X'89504E470D0A1A0A0000000D49484452000000700000005A0803000000570922290000006F504C5445DEDEDEFFFFFF9C9C9C0000009F9F9FE2E2E2A2A2A2E5E5E5CECECE8E8E8E8383832C2C2C929292DADADA1414149797976C6C6CB5B5B5414141BFBFBF717171EDEDED5C5C5C1D1D1D222222515151666666777777D4D4D47D7D7D4B4B4BF6F6F6383838ABABAB0B0B0B1C18160C0200CA7D4E1C000007A0494441546881ED59D972AB3810C5B4164020762181C1823BFFFF8DD3023B068CB9A9A9E461AA7C1E1CC70E3AEAE574B714CFFBE0830F3EF8E0830F3EF8E0830FFE3FA03BFC361D2FF30DCA2BE7BFC8C7830CE406302992FC1625F552D9A2456BD020B2224D7EC5B33C30BABCBCE2C660287FDE48CA63886E077C885AC1F8D38CF46A74704CE71002F959AFD25CA8FA3D9F63FC511B2981EE8C0E9135C1CFD9C809F47FE1BBDC1AF36326D26FF05D2E09B01F329196139CC6EF8E48BF35715F0FCF8B23CF20FC06DF8542796822E5FC1A9CE0851205E87D87F0A2A223425EB602CE508CFB1DE6FA5B7C97D41C10F21144D5476FD0F77DA46097DFBCFD96472F17FF80300927E5FB691CBE224510425804F9F6393A8CFF999047E832ABE5912BA56E9A9EF83ED68CED73497154B28F5CDABE1052A55B957551FC8AB0EF3A2162345F87FCBF1186DD0161CBD06D68C72B08090104F3FD42D43BC2745E8FB6A8C63AE2B705BC8C5ABA21CC5E95CF7BC38EC81646A5ED1F863F606F613164B1CFAC2B373725C58C096461A20D61F1AA431E16EF0953685B487D52EDD2145D5A8FA6988AB9DC7018DD5853261EBF25C39AAF9E5E9394E6961DFA13C132487B087DB24FD3A418EB3EBFA44B3DADA12E1F85AEDC109647D53B105D74103D862042B1688A085ABA8D456259D27997B4BB137AF29E4401B03561371EE9BE3A1084B60631404A52ED84D1549B27B919C22441B5CCCBDE1AFFEEC170DB23B9B8BEF261E58728DC95974A0D162120267ED1129F145B0173A53B29F5A3DCE4307A49728D9ADD8813BFAAD01106E0CAC9166C46081561AD653E6BB77BC56A71F3BC67830AF55C72FBEB26456FE2C8A38E307C9335A93484440DEAA2DFA6297A65DCAE5D23F6031CB3C9019F4B39F586900C16DDE9F643F6D554D9CBDF700376DC7E6965DF2891B5138AD0F68E30DA3C4C7370791944551685F9716FECC5B1811E1D6143F888E32CFC9010850E6043BBDD2D8F6C7D19010F125860C094AFF370F9A6DB2F698AAB93CABAFA644D776F4E8E113A427A8BB62AB37F4C841CFAACE3373A120D86EEF802F9C6A1F3B31973FEFBD367991ABE9ABF718619A7FA186BCDB4EBC1E854EDDA7E5E75FD7819256C87704CFDF77CB4B52E60AA186745F8CEBC78D012ADCE34065154C48F5F263EEE43EC08F18FC7F92CB1D6447E3A20525FA62E25157B0631152D3A1ADB53E83333A01245B7DB314602C82A27957ABEFFCB948F8FBAF235D7B047E28432126E033263A46BB0130DC32EE778683687A7DB9753AF06B2F391BBC68AE2FBD39AB08350B964B18661AFE809A9E47E90CAB25B0AE2D9FA53B310F7D005FBA96B073E14981B325A11B6D28F1AD709F1C597CAB1EEA2C255CF79A0C07EB5A6815C6E41054350F3419D1346DA39315C49BE68582C5D276C308842F824DE491F0971D0A15EAE60CAD22049122F855E0B51D4D449FBD444FC3E24BD4857845AB074F6B1EE08534E18D66EE71A6FF0DD0EA817F84ACCB7095A000B86795FD49EA8C2D56F59B14AAC8B5A332C84A44077F64EFF03ECF65CF8CB9A785048E613C1158B4B2CE7B6C2E3E24D5D5B8043D196309D5A16BA5E8FC982C29C0646DAFD70FA209C4967F0CACA450F78DAC9CF7CCA3BE9B7C58A103B218B248EA4647EB5D29919BD27BCD396709F436B03E1A94F318876D5A43027BB8510876074A7C2184760FE4AD842F645389C0771CA9A6C4588E6B0AC71847E619620C6CDAEDD0CF1BEF684D3D82C2D821743732A0C6A8A59FC0F422463C649D04D170C2B291671B1ED374E879B353883B22EAAF9C3A4C9C5791009C04AF7AC10292BECDD589CA42C2AD16CB386AB8AEF96C839F61027405AEAA46FCF7D0AB0D2FD282C56B56129E6B813D6428853F1266B78B65911ED73174149E1ECA669CB0338E19BE3BDEEFAD2B01496EE41ECE0BA46C462D84C7D3C522B429A82EFBEC5A695B85937E4549FFA948EF2498775ACC5F5BBD9C778B08818932AC6286F08897D3E4D435852885E9DE3A92D29ED0ECFF74F42BB6E4E90E1B1F73EDDF8134E9CA601BB2D8FB49C1EB32AF5D43F8FCB43EC5A1459AF94B297A964F378AF9E93146A2EC4B065D58C6C520D4E1CDDEE0614C37E5D3EE1A55457BAFA9887E86DF7E68CD0AC6588A72666647147F36792ADBFBF71A5F7A64713EC33DEF34B6A092D62EA9C7BD231E8758A5784A6F17D11E27961862BCC9C3FEE881E3745582CDC823C2F6CBE49D72EBB4F88FAA514AD084B584DDF64404580B0C506A6EABA4EA92EF6E7E5D0C29CF2A483C8DBC6762CDAC93171B31F83BCE73575DDDBF5B83F88D64CEA8E369BD10E080B423753933833135B5E7B4CA57A775186C3558AFEF0EA0C2BC3F6F28C7D611C40AC80C3B4104D232704E8887D1DAABA09532B9CF2D2C12A0D6DF9821C64EE6ED2AEAA4ABE6ECFF8CCF945A0A1B9DBF10018FF713D645CFBFD221C092A14CD44E08BD6F3BBE6F12F843F0E207145ADC5D4B833A65B5E376A4EA0FA8E5BE4B4BDC658E8E72F38F5F5DD0203A893567A7C8775A092D2F3D050342B8F3B359F1CA115F354F518F06AB0CBC937F697434C1C29B8A78AD57A5A9DC5CD801BAE0E4F4B6BDCCA319D6F0C275BB1F17ABBC43A581196B05EF37E83983EAFA4C6FD49E53D6A2F4F3B679530599A07FCF139D3D715E1E59160493032165CAF5898BE73E1BB030F42A3E52454E607DEEEF91DE10FA0C650CB36622F54BF4558429CBCF92FCD17E1BFA6559240E69544C60000000049454E44AE426082', 'images.png', 'images/png');

insert into ta_company(pkId, name, daily_payed_hours) values (1, 'Test', 7.6);

insert into ta_job_groups(pkId, job_name, description, fk_company_id) values (1, 'JIRA-TEST1', 'Job Test 1', 1);
insert into ta_job_groups(pkId, job_name, description, fk_company_id) values (2, 'JIRA-TEST2', 'Job Test 2', 1);
insert into ta_job_groups(pkId, job_name, description, fk_company_id) values (3, 'JIRA-TEST3', 'Job Test 3', 1);

insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (1, 1, DATE'2015-03-20', 7.6, 'JIRA-TEST1', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (2, 1, DATE'2015-03-21', 7.6, 'JIRA-TEST1', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (3, 1, DATE'2015-03-22', 7.6, 'JIRA-TEST1', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (4, 1, DATE'2015-03-23', 4, 'JIRA-TEST1', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (5, 1, DATE'2015-03-23', 3.6, 'JIRA-TEST1', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (6, 1, DATE'2015-03-24', 7.6, 'JIRA-TEST1', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (7, 2, DATE'2015-03-27', 7.6, 'JIRA-TEST2', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (8, 2, DATE'2015-03-28', 7.6, 'JIRA-TEST2', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (9, 2, DATE'2015-03-29', 7.6, 'JIRA-TEST2', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (10, 2, DATE'2015-03-30', 7.6, 'JIRA-TEST2', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (11, 2, DATE'2015-04-01', 4, 'JIRA-TEST2', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (12, 3, DATE'2015-04-01', 3.6, 'JIRA-TEST3', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (13, 3, DATE'2015-04-04', 7.6, 'JIRA-TEST3', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (14, 3, DATE'2015-04-05', 7.6, 'JIRA-TEST3', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (15, 3, DATE'2015-04-06', 7.6, 'JIRA-TEST3', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (16, 3, DATE'2015-04-07', 7.6, 'JIRA-TEST3', 0);
insert into ta_jobs(pkId, fk_job_group_id, job_date, worked_hours, remarks, percentage) values (17, 3, DATE'2015-04-08', 7.6, 'JIRA-TEST3', 0);