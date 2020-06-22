package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.apicontroller.OrgCategoryRestController;
import kg.tezal.tezal_back.dao.OrganizationDao;
import kg.tezal.tezal_back.entity.OrgImage;
import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.OrganizationModel;
import kg.tezal.tezal_back.model.OrganizationModelImage;
import kg.tezal.tezal_back.repository.OrgImageRepository;
import kg.tezal.tezal_back.repository.OrganizationRepository;
import kg.tezal.tezal_back.service.OrganizationService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import kg.tezal.tezal_back.utils.UtilBase64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrgImageRepository orgImageRepository;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private OrgCategoryRestController orgCategoryRestController;


    public Organization findById(Long id) {
        return organizationRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public List<Organization> findAllSorted() {
        return organizationRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Page<OrganizationModel> findAll(Pageable pageable) {
        return organizationRepository.findAllOrganizationListWithPagination(pageable);
    }

    @Override
    public Page<OrganizationModel> findAllByNameOrDescription(String search, Pageable pageable) {
        return organizationRepository.findAllByNameOrDescription(search, pageable);
    }

    public Organization create(OrganizationModelImage organizationModelImage) {
        Organization organization = new Organization();
        organization.setName(organizationModelImage.getName());

        if(organizationModelImage.getImage() != null && organizationModelImage.getImage().getContentType().contains("image"))
            organization.setImage(UtilBase64Image.encoder(organizationModelImage.getImage()));

        organization.setStatus(organizationModelImage.getStatus());
        organization.setOrgCategory(orgCategoryRestController.getOrgCategoryById(organizationModelImage.getCategoryId()));
        organization.setDescription(organizationModelImage.getDescription());
        Long id = organizationRepository.save(organization).getId();
        organization.setId(id);
        if(organizationModelImage.getImages() != null){
            for(MultipartFile image : organizationModelImage.getImages()){
                if(image.getContentType().contains("image")){
                    OrgImage orgImage = new OrgImage();
                    orgImage.setImage(UtilBase64Image.encoder(image));
                    orgImage.setOrganization(organization);
                    orgImageRepository.save(orgImage);
                }
            }
        }
        return organization;
    }


    @Override
    public void changeStatus(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
        organization.setStatus(!organization.isStatus());
        organizationRepository.save(organization);
    }

    public String deleteById(Long id) {
        organizationRepository.deleteById(id);
        return "Organization number " + id + " has been deleted!";
    }


//    public String deleteById(Long id) {
//        OrganizationModel organization = getOrganizationById(id);
//        ImageManager.deleteImage(organization.getImageUrl());
//        organizationRepository.deleteById(id);
//        return "Organization number " + id + " has been deleted!";
//    }

    public Organization putById(Long id, OrganizationModelImage organizationModelImage) {
        return organizationRepository.findById(id)
                .map(newOrganization -> {
                    newOrganization.setDescription(organizationModelImage.getDescription());
                    newOrganization.setName(organizationModelImage.getName());
                    if (organizationModelImage.getImage() != null && organizationModelImage.getImage().getContentType().contains("image"))
                        newOrganization.setImage(UtilBase64Image.encoder(organizationModelImage.getImage()));
//                    newOrganization.setClients(organization.getClients());
//                    if (organizationModelImage.getStatus()==null)
//                        newOrganization.setStatus(false);
//                    else
//                        newOrganization.setStatus(true);
                    newOrganization.setStatus(organizationModelImage.getStatus() != null);

                    Long catId = organizationModelImage.getCategoryId();
                    if (catId != null) {
                        newOrganization.setOrgCategory(orgCategoryRestController.getOrgCategoryById(organizationModelImage.getCategoryId()));
                    }
                    return organizationRepository.save(newOrganization);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }
//    public Organization putById(Long id, Organization organization) {
//        return organizationRepository.findById(id)
//                .map(newOrganization -> {
//                    newOrganization.setDescription(organization.getDesription());
//                    newOrganization.setName(organization.getName());
//                    newOrganization.setClients(organization.getClients());
//                    newOrganization.setStatus(organization.getStatus());
//                    newOrganization.setImageUrl(organization.getImage());
//                    newOrganization.setOrgCategory(organization.getOrgCategory());
//                    return organizationRepository.save(newOrganization);
//                })
//                .orElseThrow(() ->
//                        new RecordNotFoundException("Record not found with id:" + id));
//    }


    public List<OrganizationModel> findAllOrganizationList() {
        return organizationRepository.findAllOrganizationList();
    }

    public List<OrganizationModel> getOrganizationListByClientId(Long id) {
        return organizationDao.getOrgByClientId(id);
    }

    @Override
    public List<OrganizationModel> nextOrganizationListByClientIdAndCategoryId(Long clientId, Long categoryId, Long lastId, Integer limit) {
        return organizationDao.getOrgByClientIdAndCategoryId(clientId, categoryId ,lastId, limit);
    }

    public OrganizationModel getOrganizationById(Long id) {
        return organizationRepository.getOrganizationById(id);
    }

    public List<OrganizationModel> findAllOrganizationListByCategoryId(Long catId) {
        return organizationRepository.findAllOrganizationListByCategoryId(catId);
    }
}
