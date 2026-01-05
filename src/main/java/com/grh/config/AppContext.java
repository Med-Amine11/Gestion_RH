package com.grh.config;
import com.grh.service.* ;
import lombok.Getter;

public class AppContext {
    @Getter
    private static final EmployeService employeService = new EmployeService() ;
    @Getter
    private static final DepartementService departementService = new DepartementService() ;
    @Getter
    private static final ContratService contratService = new ContratService() ;
    @Getter
    private static final CongeService congeService = new CongeService() ;
    @Getter
    private static final UserService userService = new UserService() ;
    @Getter
    private static final ArchiveEmployeService archiveEmployeService = new ArchiveEmployeService() ;
    @Getter
    private static final ArchiveCongeService archiveCongeService = new ArchiveCongeService() ;
    @Getter
    private static final ArchiveContratService archiveContratService = new ArchiveContratService();
}
