package proektna.demo.web.controller;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proektna.demo.model.User;
import proektna.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping
public class Coupons {

    private final UserService userService;

    public Coupons(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/coupons")
    public String getHomePage(Model model, HttpServletRequest request) {
        String username=request.getRemoteUser();
        User user=this.userService.findByUsername(username);
        model.addAttribute("coupons",user.getCoupons());
        model.addAttribute("denari",user.getCoupons()*10);
        return "coupons";
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response,
                            HttpServletRequest request) throws DocumentException, IOException, org.dom4j.DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        String username=request.getRemoteUser();
        User user=this.userService.findByUsername(username);

        UserCouponsPdfExporter exporter = new UserCouponsPdfExporter(user);
        exporter.export(request,response);

    }

}
