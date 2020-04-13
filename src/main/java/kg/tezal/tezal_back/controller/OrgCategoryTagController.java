package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.entity.OrgCategoryTag;
import kg.tezal.tezal_back.model.OrgCategoryTagModel;
import kg.tezal.tezal_back.service.OrgCategoryService;
import kg.tezal.tezal_back.service.OrgCategoryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("org_category/{org_catId}/tag")
@Controller
public class OrgCategoryTagController {
    @Autowired
    private OrgCategoryTagService orgCategoryTagService;
    @Autowired
    private OrgCategoryService orgCategoryService;

    @GetMapping("/")
    public String getTagsByCategoryId(@PathVariable("org_catId") Long catId, Model model){
        model.addAttribute("tags",orgCategoryTagService.getTagsByCategoryId(catId));
        return "tagList";
    }
    @GetMapping("/{id}")
    public String getTagById(@PathVariable("org_catId")Long catId, @PathVariable("id") Long id, Model model){
        model.addAttribute("tag", orgCategoryTagService.getTagById(id));
        model.addAttribute("add", false);
        model.addAttribute("cat_id", catId);
        return "tagForm";
    }

    @PostMapping("/update/{id}")
    public String updateOrgCategoryTag(@PathVariable("id") Long id, @PathVariable("org_catId") Long catId,
                                       @Valid @ModelAttribute("tag") OrgCategoryTagModel tagModel,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(tagModel);
            model.addAttribute("add", false);
            return "tagForm";
        }
        orgCategoryTagService.putById(id, tagModel);
        return "redirect:/org_category/"+catId + "/tag/";
//        return "redirect:/org_category/"+catId;
    }

    @GetMapping(value = "/form")
    public String tagForm(Model model) {
        OrgCategoryTagModel tag = new OrgCategoryTagModel();
        model.addAttribute("tag", tag);
        model.addAttribute("add", true);
        return "tagForm";
    }

    @GetMapping(value = "/add")
    public String addTagFrom(Model model) {
        OrgCategoryTagModel tag = new OrgCategoryTagModel();
        model.addAttribute("tags", orgCategoryTagService.findAll());
        model.addAttribute("tag", tag);
        model.addAttribute("add", true);
        return "tagForm";
    }
    @PostMapping(value = "/create")
    public String createTag(@PathVariable("org_catId") Long catId,
                                 @Valid @ModelAttribute("tag") OrgCategoryTagModel tagModel,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(tagModel);
            model.addAttribute("add", true);
            return "tagForm";
        }
        OrgCategoryTag tag = orgCategoryTagService.create(tagModel);
        orgCategoryService.addTag(catId, tag);
        return "redirect:/org_category/"+catId + "/tag/";
//        return "redirect:/org_category/" + catId;
    }
    @PostMapping(value = "/adding")
    public String addTag(@PathVariable("org_catId") Long catId,
                                 @Valid @ModelAttribute("tag") OrgCategoryTagModel tagModel) {

        OrgCategoryTag tag = orgCategoryTagService.findById(tagModel.getId());
        orgCategoryService.addTag(catId, tag);
        return "redirect:/org_category/"+catId + "/tag/";
//        return "redirect:/org_category/" + catId;
    }

    @PostMapping("/delete/{id}")
    public String deleteOrgCategory(@PathVariable("org_catId")Long catId,@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            orgCategoryTagService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            return "tagForm";
        }
//        return "redirect:/org_category/" + catId;
        return "redirect:/org_category/"+catId + "/tag/";

    }
}
