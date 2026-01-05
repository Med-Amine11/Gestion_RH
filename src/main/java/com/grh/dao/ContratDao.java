package com.grh.dao;

import com.grh.model.Contrat;
import java.util.List;

public interface ContratDao {
    int addContrat(Contrat contrat);
    int updateContrat(Contrat contrat);
    int deleteContrat(int id);
    Contrat findContratById(int id);
    List<Contrat> findAllContrats();
    List<Contrat> findContratsByEmployeId(int idEmploye);
}