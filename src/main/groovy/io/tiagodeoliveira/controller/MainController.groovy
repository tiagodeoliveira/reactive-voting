package io.tiagodeoliveira.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by tiago on 04/07/15.
 */
@Controller
class MainController {
    @RequestMapping('/')
    public String index(Model model) {
        return 'index'
    }

    @RequestMapping('/admin')
    public String admin(Model model) {
        return 'admin'
    }

}
