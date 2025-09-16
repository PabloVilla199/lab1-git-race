# Lab 1 Git Race -- Project Report

## Description of Changes
Internationalization (i18n) has been implemented in the application, providing support for both languages English and Spanish. 
This makes the application more scalable, as additional languages can be easily added in the future. 
The existing tests have been adapted to work with internationalization, and new tests have been added  
to ensure that the implementation is correct and that the language changes according to the request.

The changes were made in the following order:

1. **Properties**: first, the message files (`messages.properties` and `messages_es.properties`) were added and adapted to contain texts in English and Spanish.
2. **SpringBoot Configuration**: the `WebConfig` class was created with `SessionLocaleResolver` to define the default language and `LocaleChangeInterceptor` to detect language changes in requests. The interceptor was also registered using `addInterceptors`.
3. **Frontend (HTML)**: Welcome template was modified so that texts were not hardcoded, using  Thymeleaf resources to display messages according to the active language.
4. **Controllers (HelloController and HelloApiController)**:Controllers were adapted to use the received `Locale` and `MessageSource`, returning the correct texts based on the session's active language.
5. **Tests**: existing tests were modified to work with internationalization, and new tests were added to verify that the language changes correctly based on the request.
 

## Technical Decisions

1. **Choosing to implement internationalization:** At first, I wanted to create personalized “hello world” messages depending on the day, which seemed simple and could be done just by modifying the controller. In the end, I chose to implement internationalization (i18n) instead, supporting both English and Spanish. I thought this was more valuable since i18n is an important part of interface design and something every real application should have. Plus, I hadn’t worked with it before, so it was a great opportunity to learn.

2. **Consulting Spring Boot documentation:** As I had no prior experience with internationalization in Spring Boot, 
     I relied on the official documentation to understand how to configure the required components, such as `SessionLocaleResolver` and `LocaleChangeInterceptor`,                              and how to integrate them into the controllers and templates.

3. **Using a FEATURE branch:** I decided to create a FEATURE branch separate from MAIN to develop and test the internationalization until it worked correctly, 
    and then merge it into MAIN. This allowed me to keep the main branch stable and practice proper Git workflow.

4. **Adapting and adding tests:** I adapted the existing tests to work with internationalization and added new tests to verify that the language changes correctly 
according to the request parameters. This ensured the feature works as expected across different locales.

## Learning Outcomes

- **Internationalization (i18n) in Spring Boot:** I Implemented for the first time, learning both the concept and how to apply it in Spring Boot.  

- **SessionLocaleResolver:** Sets the default locale (English) and keeps track of the user's selected locale during their session. This ensures that the language preference persists across requests.  

- **LocaleChangeInterceptor:** Checks each HTTP request for a `lang` parameter and updates the active locale accordingly. This allows the user to change the language dynamically.  

- **addInterceptors / InterceptorRegistry:** Registers the interceptor so that it runs on every request. Think of it like a round-robin mechanism: each request passes through the interceptor, which checks if the language has changed and applies the correct locale.  

- **MessageSource:** Resolves messages based on the active locale, fetching the correct text from properties files so that the content displayed to the user matches their language selection.  

- **Learning Interceptors on Spring boot:** I learned how interceptors work internally in Spring Boot, which I didn’t know before. Now I understand that they are executed for each request, allowing functions like language switching to happen automatically.  

- **Git Workflow:** I improved  my skills in creating branches, making clear commits, and merging a FEATURE branch into MAIN.  

- **Testing:**  I adapted  the existing tests and created new ones to verify the internationalization functionality.  

- **Frontend modification:** Learned how to dynamically adapt the frontend to support multiple languages, understanding the interaction between backend locales and frontend rendering.



## AI Disclosure
### AI Tools Used
ChatGPT

Cloude

### AI-Assisted Work
- IA served as support to choose the topic, explore possible new features, and understand conceptually how each could be implemented.
- Once I decided to implement internationalization, I used the Spring Boot documentation and provided it to IA to help me understand how it worked.
- It also helped me understand how interceptors work in Spring Boot, which I did not know before.
- Thanks to IA, I could see which tests were necessary and how to improve them, although I had to modify and adapt the code it generated for the tests to make sense in my implementation.
- IA 20%, My Work 80%.


### Original Work
My work consisted in implementing the internationalization feature in the application. I first explored the Spring Boot documentation to understand how to configure localization and message handling. 

I created the WebConfig file, defining the SessionLocaleResolver, LocaleChangeInterceptor, and registering interceptors for each request. I implemented the necessary changes in the controllers to return messages according to the active locale.

I also modified existing tests to make them compatible with internationalization and created new tests to verify that the feature worked correctly in both English and Spanish.

Additionally, I improved the frontend to dynamically display text according to the selected language.

Throughout this assignment, I learned how internationalization works in Spring Boot, including how MessageSource, LocaleResolver, and interceptors interact to deliver localized content. I also practiced Git workflow best practices, including creating feature branches, making clear commits, and merging into main while maintaining a clean history.
