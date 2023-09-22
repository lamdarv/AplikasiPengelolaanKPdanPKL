package com.jtk.ps.api.unit;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.repository.*;
import com.jtk.ps.api.service.CompanyService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {CompanyServiceTest.class})
public class CompanyServiceTest {
    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PrerequisiteRepository prerequisiteRepository;

    @Mock
    private ProposerRepository proposerRepository;

    @Mock
    private CriteriaRepository criteriaRepository;

    @Mock
    private PrerequisiteCompetenceRepository prerequisiteCompetenceRepository;

    @Mock
    private PrerequisiteJobscopeRepository prerequisiteJobscopeRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private AdvantageRepository advantageRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SubmissionCriteriaRepository submissionCriteriaRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private ValuationRepository valuationRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getAllCompaniesTest(){
        List<Integer> listAccount = new ArrayList<>(List.of(1,2,3));

        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4));
        companyList.add(new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5));
        companyList.add(new Company(3, "Company C", "Email C", "Address C",
                "Phone C", "CP Name C", "CP Phone C", "CP EMail C",
                "CP Position C", "Website C", 300, 2010, Boolean.TRUE,
                3,6));

        Submission submission1 = new Submission(1, "Company A", "Email A", "Address A", "Phone A", "CP Name A", "CP Phone A", "CP EMail A", "CP Position A", "Website A", 2008, 100, "WFH", "D3", 2022,Boolean.FALSE);
        Submission submission2 = new Submission(2, "Company B", "Email B", "Address B", "Phone B", "CP Name B", "CP Phone B", "CP EMail B", "CP Position B", "Website B", 2009, 200, "WFH", "D3", 2022,Boolean.FALSE);
        Submission submission3 = new Submission(3, "Company C", "Email C", "Address C", "Phone C", "CP Name C", "CP Phone C", "CP EMail C", "CP Position C", "Website C", 2010, 300, "WFH", "D3", 2022,Boolean.FALSE);


        Mockito.when(companyRepository.findByAccountIdIn(any(List.class))).thenReturn(companyList);

        Mockito.when(proposerRepository.findByCompanyIdId(companyList.get(0).getId())).thenReturn(Optional.of(new Proposer(1, "Proposer A", companyList.get(0), submission1)));
        Mockito.when(proposerRepository.findByCompanyIdId(companyList.get(1).getId())).thenReturn(Optional.of(new Proposer(2, "Proposer B", companyList.get(1), submission2)));
        Mockito.when(proposerRepository.findByCompanyIdId(companyList.get(2).getId())).thenReturn(Optional.of(new Proposer(3, "Proposer C", companyList.get(2), submission3)));

        List<CompanyResponse> companyResponseList = companyService.getAllCompanies(listAccount);

        Mockito.verify(companyRepository, Mockito.times(1)).findByAccountIdIn(any(List.class));
        assertEquals(3,companyResponseList.size());
    }

    @Test
    void getCompaniesTest(){
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4));
        companyList.add(new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5));
        companyList.add(new Company(3, "Company C", "Email C", "Address C",
                "Phone C", "CP Name C", "CP Phone C", "CP EMail C",
                "CP Position C", "Website C", 300, 2010, Boolean.TRUE,
                3,6));

        Mockito.when(companyRepository.findAll()).thenReturn(companyList);

        Submission submission1 = new Submission(1, "Company A", "Email A", "Address A", "Phone A", "CP Name A", "CP Phone A", "CP EMail A", "CP Position A", "Website A", 2008, 100, "WFH", "D3", 2022,Boolean.FALSE);
        Submission submission2 = new Submission(2, "Company B", "Email B", "Address B", "Phone B", "CP Name B", "CP Phone B", "CP EMail B", "CP Position B", "Website B", 2009, 200, "WFH", "D3", 2022,Boolean.FALSE);
        Submission submission3 = new Submission(3, "Company C", "Email C", "Address C", "Phone C", "CP Name C", "CP Phone C", "CP EMail C", "CP Position C", "Website C", 2010, 300, "WFH", "D3", 2022,Boolean.FALSE);

        Mockito.when(proposerRepository.findByCompanyIdId(companyList.get(0).getId())).thenReturn(Optional.of(new Proposer(1, "Proposer A", companyList.get(0), submission1)));
        Mockito.when(proposerRepository.findByCompanyIdId(companyList.get(1).getId())).thenReturn(Optional.of(new Proposer(2, "Proposer B", companyList.get(1), submission2)));
        Mockito.when(proposerRepository.findByCompanyIdId(companyList.get(2).getId())).thenReturn(Optional.of(new Proposer(3, "Proposer C", companyList.get(2), submission3)));

        List<CompanyResponse> companyResponseList = companyService.getCompanies();

        System.out.println(companyResponseList);

        Mockito.verify(companyRepository, Mockito.times(1)).findAll();
        Mockito.verify(proposerRepository, Mockito.times(1)).findByCompanyIdId(1);
        Mockito.verify(proposerRepository, Mockito.times(1)).findByCompanyIdId(2);
        Mockito.verify(proposerRepository, Mockito.times(1)).findByCompanyIdId(3);

        assertEquals(3,companyResponseList.size());
    }

    @Test
    void updateCompanyPicTest(){
        Integer id = 1;
        companyService.updateCompanyPic(id);

        Mockito.verify(companyRepository, Mockito.times(1)).updateLecturerIdByLecturerId(id);
    }

    @Test
    void getCompaniesAndPrerequisiteByProdi(){
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4));
        companyList.add(new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5));

        Mockito.when(companyRepository.findAll()).thenReturn(companyList);

        Mockito.when(prerequisiteRepository.findByCompanyIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(new Prerequisite());

        ListCompany listCompany = companyService.getCompaniesAndPrerequisiteByProdi(1);

        assertEquals(2, listCompany.getCompany().size());
        assertEquals(1, listCompany.getTotalCompanyActive());
        assertEquals(1, listCompany.getTotalCompanyInactive());

        Mockito.verify(companyRepository, Mockito.times(1)).findAll();
        Mockito.verify(prerequisiteRepository, Mockito.times(2)).findByCompanyIdAndYear(any(Integer.class),any(Integer.class));
    }

    @Test
    void createCompanyTest(){
        CompanyRequest companyRequest = new CompanyRequest("Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                4);

        String cookie = "cookie";

        CreateAccountResponse createAccountResponse = new CreateAccountResponse(1, "companyA", "1234", "COMPANY");
        Response<CreateAccountResponse> response = new Response<>(createAccountResponse, HttpStatus.OK.value(), null);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/account/create"),
                any(HttpMethod.class),any(), any(ParameterizedTypeReference.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        Mockito.when(companyRepository.saveAndFlush(any(Company.class))).thenReturn(new Company(0, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A", "CP Position A", "Website A", 100, 2008, Boolean.TRUE,1,4));


        Company company = companyService.createCompany(companyRequest, cookie);

        assertEquals("Company A", company.getCompanyName());
        assertEquals("Email A", company.getCompanyEmail());
        assertEquals("Address A", company.getAddress());
        assertEquals("Phone A", company.getNoPhone());
        assertEquals("CP Name A", company.getCpName());
        assertEquals("CP Phone A", company.getCpPhone());
        assertEquals("CP EMail A", company.getCpEmail());
        assertEquals("CP Position A", company.getCpPosition());
        assertEquals("Website A", company.getWebsite());
        assertEquals(100, company.getNumEmployee());
        assertEquals(2008, company.getSinceYear());
        assertEquals(Boolean.TRUE, company.getStatus());
        assertEquals(4, company.getLecturerId());

        Mockito.verify(companyRepository, Mockito.times(1)).saveAndFlush(any(Company.class));
        Mockito.verify(prerequisiteRepository, Mockito.times(1)).save(any(Prerequisite.class));
    }

    @Test
    void getCardPrerequisiteByCompanyTest(){
        String cookie = "cookie";

        Company company = new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5);

        Prerequisite prerequisite = new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company, 1000, "Project Test");
        Mockito.when(prerequisiteRepository.findByCompanyIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(prerequisite);

        FormSubmitTimeResponse formSubmitTimeResponse = new FormSubmitTimeResponse(1, "Mulai KP","04/04/2022", "04/06/2022", 1);
        Response<FormSubmitTimeResponse> response = new Response<>(formSubmitTimeResponse, HttpStatus.OK.value(), null);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/management-content/form-submit-time/"),
                any(HttpMethod.class),any(), any(ParameterizedTypeReference.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        PrerequisiteCard prerequisiteCard = companyService.getCardPrerequisiteByCommittee(cookie, company.getId());

        assertEquals("Company B", prerequisiteCard.getCompanyName());
        assertEquals(Boolean.TRUE, prerequisiteCard.getStatusPrerequisite());
        assertEquals(1, prerequisiteCard.getIdPrerequisite());

        Mockito.verify(prerequisiteRepository, Mockito.times(1)).findByCompanyIdAndYear(any(Integer.class), any(Integer.class));
    }

    @Test
    void getQuotaTest(){
        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Company company2 = new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5);


        List<Prerequisite> prerequisiteList = new ArrayList<>();
        prerequisiteList.add(new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company1, 1000, "Project Test"));
        prerequisiteList.add(new Prerequisite(2, "Practical Address B", "Industrial Advisor B", "Industrial Advision Position", "Industrial Advisor Mail","Facility B", 10, 5, "WFH/WFO", "Description B", 2022,Boolean.TRUE, company2, 1000, "Project Test"));

        Mockito.when(prerequisiteRepository.findByYear(any(Integer.class))).thenReturn(prerequisiteList);

        List<QuotaResponse> prerequisiteQuotaList = companyService.getQuota();
        assertEquals(2, prerequisiteQuotaList.size());
    }

    @Test
    void getPrerequisiteTest(){
        Integer idPrerequisite = 1;
        String cookie = "cookie";

        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Mockito.when(prerequisiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Prerequisite(idPrerequisite,
                "Practical Address A", "Industrial Advisor A", "Industrial Advisor Position",
                "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A",
                2022,Boolean.TRUE, company1, 1000, "Project Test")));

        Mockito.when(prerequisiteCompetenceRepository.findByPrerequisiteId(any(Integer.class))).thenReturn(new ArrayList<>());
        Mockito.when(prerequisiteJobscopeRepository.findByPrerequisiteId(any(Integer.class))).thenReturn(new ArrayList<>());

        PrerequisiteResponse prerequisiteResponse = companyService.getPrerequisite(idPrerequisite, cookie);

        assertEquals("Company A", prerequisiteResponse.getCompanyName());
        assertEquals("Practical Address A", prerequisiteResponse.getPracticalAddress());
        assertEquals("Industrial Advisor A", prerequisiteResponse.getInAdvisorName());
        assertEquals("Industrial Advisor Position", prerequisiteResponse.getInAdvisorPosition());
        assertEquals("Industrial Advisor Mail", prerequisiteResponse.getInAdvisorMail());
        assertEquals("Facility A", prerequisiteResponse.getFacility());
        assertEquals(10, prerequisiteResponse.getTotalD3());
        assertEquals(5, prerequisiteResponse.getTotalD4());
        assertEquals("WFH/WFO", prerequisiteResponse.getWorkSystem());
        assertEquals("Description A", prerequisiteResponse.getDescription());
        assertEquals(2022, prerequisiteResponse.getYear());
        assertEquals(Boolean.TRUE, prerequisiteResponse.getStatus());
        assertEquals(1000, prerequisiteResponse.getRegionId());

        Mockito.verify(prerequisiteCompetenceRepository, Mockito.times(1)).findByPrerequisiteId(any(Integer.class));
        Mockito.verify(prerequisiteJobscopeRepository, Mockito.times(1)).findByPrerequisiteId(any(Integer.class));
    }

    @Test
    void updatePrerequisiteByCompanyTest(){
        PrerequisiteRequest prerequisiteRequest = new PrerequisiteRequest("Practical Address A", "Industrial Advisor A", "Industrial Advisor Position",
                "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 1,2000, new ArrayList<>(), new ArrayList<>(), "Project Test");

        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Mockito.when(prerequisiteRepository.findByCompanyIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company1, 1000, "Project Test"));


        Boolean isSuccess = companyService.updatePrerequisiteByCompany(prerequisiteRequest, 1);

        assertEquals(Boolean.TRUE, isSuccess);
        assertEquals("Practical Address A", prerequisiteRequest.getPracticalAddress());

        Mockito.verify(prerequisiteRepository, Mockito.times(1)).findByCompanyIdAndYear(any(Integer.class), any(Integer.class));
        Mockito.verify(prerequisiteCompetenceRepository, Mockito.times(1)).deleteAllByPrerequisiteId(any(Integer.class));
        Mockito.verify(prerequisiteCompetenceRepository, Mockito.times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void updatePrerequisiteByCommitteeTest(){
        Integer idPrerequisite = 1;
        PrerequisiteRequest prerequisiteRequest = new PrerequisiteRequest("Practical Address A", "Industrial Advisor A", "Industrial Advisor Position",
                "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 1,2000, new ArrayList<>(), new ArrayList<>(), "Project Test");

        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Prerequisite prerequisite = new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company1, 1000, "Project Test");

        Mockito.when(prerequisiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(prerequisite));

        Boolean isSuccess = companyService.updatePrerequisiteByCommittee(idPrerequisite, prerequisiteRequest, "cookie");

        assertEquals(Boolean.TRUE, isSuccess);

        Mockito.verify(prerequisiteCompetenceRepository, Mockito.times(1)).deleteAllByPrerequisiteId(any(Integer.class));
        Mockito.verify(prerequisiteCompetenceRepository, Mockito.times(1)).saveAll(new ArrayList<>());
    }

    @Test
    void markAsDoneByCommitteeTest(){
        Integer idPrerequisite = 1;

        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Prerequisite prerequisite = new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company1, 1000, "Project Test");

        Mockito.when(prerequisiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(prerequisite));

        Boolean isSuccess = companyService.markAsDoneByCommittee(idPrerequisite);

        assertEquals(Boolean.TRUE, isSuccess);

        Mockito.verify(prerequisiteRepository, Mockito.times(1)).save(any(Prerequisite.class));
    }

    @Test
    void markAsDoneByCompanyTest(){
        Integer idCompany = 2;

        Company company = new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5);

        Prerequisite prerequisite = new Prerequisite(1, "Practical Address A", "Industrial Advisor A",
                "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5,
                "WFH/WFO", "Description A", 2022,Boolean.TRUE, company, 1000, "Project Test");
        Mockito.when(prerequisiteRepository.findByCompanyIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(prerequisite);

        Boolean isSuccess = companyService.markAsDoneByCompany(idCompany);

        assertEquals(Boolean.TRUE, isSuccess);

        Mockito.verify(prerequisiteRepository, Mockito.times(1)).save(any(Prerequisite.class));
    }

    @Test
    void getCompanyNameByIdTest(){
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4));
        companyList.add(new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5));
        companyList.add(new Company(3, "Company C", "Email C", "Address C",
                "Phone C", "CP Name C", "CP Phone C", "CP EMail C",
                "CP Position C", "Website C", 300, 2010, Boolean.TRUE,
                3,6));

        Mockito.when(companyRepository.findByStatus(true)).thenReturn(companyList);

        List<CompanyName> companyNames = companyService.getCompanyNameById();

        assertEquals(3, companyNames.size());
        assertEquals("Company A", companyNames.get(0).getName());
        assertEquals("Company B", companyNames.get(1).getName());
        assertEquals("Company C", companyNames.get(2).getName());
        assertEquals(1, companyNames.get(0).getId());
        assertEquals(2, companyNames.get(1).getId());
        assertEquals(3, companyNames.get(2).getId());
    }

    @Test
    void getCardPrerequisiteByCommitteeTest(){
        String cookie = "cookie";

        Company company = new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.FALSE,
                2,5);

        Prerequisite prerequisite = new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company, 1000, "Project Test");
        Mockito.when(prerequisiteRepository.findByCompanyIdAndYear(any(Integer.class), any(Integer.class))).thenReturn(prerequisite);

        FormSubmitTimeResponse formSubmitTimeResponse = new FormSubmitTimeResponse(1, "Mulai KP","04/04/2022", "04/06/2022", 1);
        Response response = new Response(formSubmitTimeResponse, HttpStatus.OK.value(), null);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/management-content/form-submit-time/"),
                any(HttpMethod.class),any(), any(ParameterizedTypeReference.class))).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        PrerequisiteCard prerequisiteCard = companyService.getCardPrerequisiteByCommittee(cookie, company.getId());

        assertEquals("Company B", prerequisiteCard.getCompanyName());
        assertEquals(Boolean.TRUE, prerequisiteCard.getStatusPrerequisite());
        assertEquals(1, prerequisiteCard.getIdPrerequisite());

        Mockito.verify(prerequisiteRepository, Mockito.times(1)).findByCompanyIdAndYear(any(Integer.class), any(Integer.class));
    }

    @Test
    void getCompanyByIdTest(){
        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Mockito.when(companyRepository.findById(any(Integer.class))).thenReturn(Optional.of(company1));

        Mockito.when(proposerRepository.findByCompanyIdId(any(Integer.class))).thenReturn(Optional.of(new Proposer(1, "Proposer A", company1, null)));

        CompanyResponse company = companyService.getCompanyById(1);

        assertEquals("Company A", company.getCompanyName());
        assertEquals("Email A", company.getCompanyEmail());
        assertEquals("Address A", company.getAddress());
        assertEquals("Phone A", company.getTelp());
        assertEquals("CP Name A", company.getCpName());
        assertEquals("CP Phone A", company.getCpTelp());
        assertEquals("CP EMail A", company.getCpEmail());
        assertEquals("CP Position A", company.getCpPosition());
        assertEquals("Website A", company.getWebsite());
        assertEquals(100, company.getNumEmployee());
        assertEquals(2008, company.getSinceYear());
        assertEquals(Boolean.TRUE, company.getStatus());
    }

    @Test
    void getNameAndIdCompaniesTest(){
        Integer idProdi = 0;

        Company company1 = new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4);

        Company company2 = new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.TRUE,
                2,5);


        List<Prerequisite> prerequisiteList = new ArrayList<>();
        prerequisiteList.add(new Prerequisite(1, "Practical Address A", "Industrial Advisor A", "Industrial Advision Position", "Industrial Advisor Mail","Facility A", 10, 5, "WFH/WFO", "Description A", 2022,Boolean.TRUE, company1, 1000, "Project Test"));
        prerequisiteList.add(new Prerequisite(2, "Practical Address B", "Industrial Advisor B", "Industrial Advision Position", "Industrial Advisor Mail","Facility B", 10, 5, "WFH/WFO", "Description B", 2022,Boolean.TRUE, company2, 1000, "Project Test"));

        Mockito.when(prerequisiteRepository.findByYearEqualsAndTotalD3GreaterThan(any(Integer.class), any(Integer.class))).thenReturn(prerequisiteList);


        List<CompanyIdName> companyList = companyService.getNameAndIdCompanies(idProdi);

        System.out.println(companyList);

        assertEquals(2, companyList.size());
        assertEquals(1, companyList.get(0).getId());
        assertEquals("Company A", companyList.get(0).getName());
        assertEquals(2, companyList.get(1).getId());
        assertEquals("Company B", companyList.get(1).getName());

        Mockito.verify(prerequisiteRepository, Mockito.times(1)).findByYearEqualsAndTotalD3GreaterThan(any(Integer.class), any(Integer.class));
    }

    @Test
    void deletePrerequisiteCompetenceTest(){
        companyService.deletePrerequisiteCompetence(any(Integer.class));
        Mockito.verify(prerequisiteCompetenceRepository, Mockito.times(1)).deleteAllByCompetenceId(any(Integer.class));
    }

    @Test
    void deletePrerequisiteJobscopeTest(){
        companyService.deletePrerequisiteJobscope(any(Integer.class));
        Mockito.verify(prerequisiteJobscopeRepository, Mockito.times(1)).deleteAllByJobscopeId(any(Integer.class));
    }

    @Test
    void getCompanyCriteriaTest(){
        companyService.getCompanyCriteria();
        Mockito.verify(criteriaRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createCriteriaTest(){
        CriteriaRequest criteriaRequest = new CriteriaRequest("Criteria 1");
        companyService.createCriteria(criteriaRequest);
        Mockito.verify(criteriaRepository, Mockito.times(1)).save(any(Criteria.class));
    }

    @Test
    void updateCriteriaTest(){
        CriteriaRequest criteriaRequest = new CriteriaRequest("Criteria 2");
        Mockito.when(criteriaRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Criteria(1, "Criteria 1")));
        companyService.updateCriteria(1, criteriaRequest);
        Mockito.verify(criteriaRepository, Mockito.times(1)).save(any(Criteria.class));
    }

    @Test
    void deleteCriteriaTest(){
        companyService.deleteCriteria(any(Integer.class));

        Mockito.verify(submissionCriteriaRepository, Mockito.times(1)).deleteByCriteriaId(any(Integer.class));
        Mockito.verify(criteriaRepository, Mockito.times(1)).deleteById(any(Integer.class));
    }

    @Test
    @Disabled
    void getCompanyRequirementsTest(){
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 100, 2008, Boolean.TRUE,
                1,4));

        companies.add(new Company(2, "Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 200, 2009, Boolean.TRUE,
                2,5));

        Mockito.when(companyRepository.findByStatus(true)).thenReturn(companies);

        List<CompanyResponse> companyResponses = new ArrayList<>();
        companyResponses.add(new CompanyResponse(1, 1,"Company A", "Email A", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 2000, Boolean.TRUE, new ArrayList<>(),
                1,4));
        companyResponses.add(new CompanyResponse(2, 2,"Company B", "Email B", "Address B",
                "Phone B", "CP Name B", "CP Phone B", "CP EMail B",
                "CP Position B", "Website B", 2000, Boolean.TRUE, new ArrayList<>(),
                2,5));

        ResponseList<CompanyResponse> responseList = new ResponseList<>();
        responseList.setData(companyResponses);
        responseList.setStatus(200);
        responseList.setMessage("Success");

        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/management-content/competence/get-all/0"),
                any(HttpMethod.class),any(), any(ParameterizedTypeReference.class))).thenReturn(new ResponseEntity<>(responseList, HttpStatus.OK));

//        companyService.getCompanyRequirements();
//        Mockito.verify(requirementRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getCompanySubmissionTest(){
        List<Submission> submissions = new ArrayList<>();
        submissions.add(new Submission(1, "Company Name A", "Company Email", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 2088, 100, "WFH", "D3",
                1,false));

        Mockito.when(submissionRepository.findAllByYearKpPklAndIsDeleted(any(Integer.class), any())).thenReturn(submissions);

        List<SubmissionResponse> submissionResponses = companyService.getCompanySubmission();

        assertEquals(1, submissionResponses.size());
        assertEquals(1, submissionResponses.get(0).getId());
        assertEquals("Company Name A", submissionResponses.get(0).getCompanyName());
        assertEquals("CP Name A", submissionResponses.get(0).getCpName());
        assertEquals("CP Phone A", submissionResponses.get(0).getCpPhone());
        assertEquals("CP EMail A", submissionResponses.get(0).getCpMail());
    }

    @Test
    @Disabled
    void getDetailCompanySubmissionTest(){

    }

    @Test
    @Disabled
    void createCompanySubmissionTest(){

    }

    @Test
    @Disabled
    void acceptCompanySubmissionTest(){

    }

    @Test
    void declineCompanySubmissionTest(){
        Mockito.when(submissionRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Submission(1, "Company Name A", "Company Email", "Address A",
                "Phone A", "CP Name A", "CP Phone A", "CP EMail A",
                "CP Position A", "Website A", 2088, 100, "WFH", "D3",
                1,false)));

        companyService.declineCompanySubmission(1);

        assertEquals(true, submissionRepository.findById(1).get().getIsDeleted());

        Mockito.verify(submissionRepository, Mockito.times(1)).save(any(Submission.class));
    }

    @Test
    @Disabled
    void getEvaluationCardByCompanyTest(){}

    @Test
    @Disabled
    void getEvaluationDetailTest(){}

    @Test
    void createEvaluationTest(){
        List<CreateEvaluationRequest> createEvaluationRequests = new ArrayList<>();
        createEvaluationRequests.add(new CreateEvaluationRequest(1,1,0,"Position 1"));
        createEvaluationRequests.add(new CreateEvaluationRequest(2,2,0,"Position 2"));
        createEvaluationRequests.add(new CreateEvaluationRequest(3,3,0,"Position 3"));

        companyService.createEvaluation(createEvaluationRequests);

        Mockito.verify(evaluationRepository, Mockito.times(3)).save(any(Evaluation.class));
    }

    @Test
    void updateEvaluationTest(){
        Mockito.when(evaluationRepository.findByIdParticipantAndNumEvaluation(any(Integer.class), any(Integer.class))).thenReturn(new Evaluation(1,"Comment 1", 2022, 1, 0, "Position 1", 0, 1, 1));

        companyService.updateEvaluation(1,new UpdateEvaluationRequest(1, "Coment 1", new ArrayList<>(), "Position 1"));
        Mockito.verify(evaluationRepository, Mockito.times(1)).save(any(Evaluation.class));
    }

    @Test
    void deleteAllEvaluationTest(){
        companyService.deleteAllEvaluation(1);

        Mockito.verify(evaluationRepository, Mockito.times(1)).deleteAllByYearAndIdProdi(any(Integer.class), any(Integer.class));
    }

    @Test
    @Disabled
    void getPrerequisitesCompanyTest(){}

    @Test
    @Disabled
    void generatePdfEvaluationTest(){}

    @Test
    @Disabled
    void getEvaluationFormTest(){}

    @Test
    @Disabled
    void changeStatusTest(){}

    @Test
    @Disabled
    void getEvaluationTableByCommitteeTest(){}







}
