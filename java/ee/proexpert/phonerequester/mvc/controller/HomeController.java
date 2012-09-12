/*
 * Copyright 2002-2012 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package ee.proexpert.phonerequester.mvc.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.proexpert.phonerequester.model.PhoneReq;
import ee.proexpert.phonerequester.service.PhoneRequestService;

/**
 * Handles requests for the application home page.
 *
 * @author Your Name Here
 * @version 1.0
 *
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Autowired
	private PhoneRequestService phoneRequestService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/ajax")
	public  @ResponseBody PhoneReq ajaxCall(Model model) {

		final PhoneReq phoneReq = phoneRequestService.getLastPhoneReq();

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Last PhoneReq:"+phoneReq);
		}

		return phoneReq;

	}
}

