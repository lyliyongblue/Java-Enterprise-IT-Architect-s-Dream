package com.yong.spring.jpa.jms.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class SpringMvcExceptionHandler implements HandlerExceptionResolver {
	private final Logger logger = LoggerFactory.getLogger(SpringMvcExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String msg;
        if(ex instanceof BusinessException) {
            msg = ex.getMessage();
        } else if (ex instanceof IllegalArgumentException) {
            msg = ex.getMessage();
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validEx = (MethodArgumentNotValidException) ex;
            msg = Objects.requireNonNull(validEx.getBindingResult().getFieldError()).getDefaultMessage();
        } else if (ex instanceof BindException) {
            BindException validEx = (BindException) ex;
            msg = Objects.requireNonNull(validEx.getBindingResult().getFieldError()).getDefaultMessage();
        } else {
            msg = "系统异常，请联系客服";
            logger.error(ex.getMessage(), ex);
        }
        return jsonHandler(response, msg);
	}

    private ModelAndView jsonHandler(HttpServletResponse response, String msg) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        mv.setView(view);
        mv.addObject("code", "0");
        mv.addObject("msg", msg);
        mv.addObject("data", null);
        return mv;
    }
}
