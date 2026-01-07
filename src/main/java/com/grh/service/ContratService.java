package com.grh.service;

import com.grh.dao.ContratDao;
import com.grh.dao.ContratDaoImp;
import com.grh.model.Contrat;
import java.util.List;

public class ContratService {

    private ContratDao contratDao;

    public ContratService() {
        this.contratDao = new ContratDaoImp();
    }

    public boolean addContrat(Contrat contrat) {
        return contratDao.addContrat(contrat) > 0;
    }

    public boolean updateContrat(Contrat contrat) {
        return contratDao.updateContrat(contrat) > 0;
    }

    public boolean deleteContrat(int id) {
        return contratDao.deleteContrat(id) > 0;
    }

    public Contrat getContratById(int id) {
        return contratDao.findContratById(id);
    }

    public List<Contrat> findAllContrats() {
        return contratDao.findAllContrats();
    }

    public List<Contrat> getContratsByEmployeId(int idEmploye) {
        return contratDao.findContratsByEmployeId(idEmploye);
    }

    public Boolean archiverContrat(Contrat contrat){
        return contratDao.archiverContrat(contrat) > 0 ;
    }

}