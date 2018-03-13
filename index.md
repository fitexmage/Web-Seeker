# Project Proposal

## Project Description
My project is a website which provides a platform for discussion about web pages. It allows users to seek for the web page they want and to share their feeling about the one they like or dislike. Based on the category or tags, users can easily find their web pages by their features. From the basic information provided by others, users can learn more about the web pages. Users can also provide web pages by their own and give rating or review to every web page. 

## Project Design

### Technology
In the project, I will use Maven to carry all the dependencies. Then, I will use Spring MVC as the basic structure and use MySQL as the database. The project will deploy with Tomcat. To avoid the block caused by a large population, I will use concurrency. Moreover, I may use some searching algorithm to deal with the functionality about searching web pages. To integrate technologies, I will install all the dependencies in Maven, and use ORM to connect Spring MVC with the MySQL database. For each request, I will put it into thread, and use "synchronized" or other code on the shared data. Then, I will upload my code to Tomcat, and run on that server. 

### Use Case
On the website, users can register for an account and login using it. After login, users can search for web pages using the search bar or look for the one they want by the category. Then, users can see the detailed information about the web page, such as its description, category, tags, rating, and reviews. Here, users can rate the web page and write any reviews they want. On the home page, users can also see the rank of rating of web page which in different categories or tags, and the recommendation about what they may like based on the history. In addition, users can provide the information of a web page by their own. They can create a new web page, such as the name, URL, description, and tags, for others to read and rate. If some web pages provide wrong information, users can report it or request to modify it.

## Project Schedule

### Project Milestone 1
Allow users to register and login, allow users to see and modify their account information, allow users to provide the information of a new web page on the website, allow users to access into web page on the list and read the information.

### Project Milestone 2
Allow users to rate and write reviews for the web pages, allow users to "like" and "dislike" other reviews, store users' exploring history, display the latest created web page on the home page, display categories and tags on the home page for users to choose, and then display and the rank of rating of web page.

### Project Milestone 3
Allow users to use search bar to search for name or tags of web pages, allow users to report or request to modify the web pages, allow users to add friends and recommend web pages to their friends, give users recommendation based on history.

### Finaly Project
The final project will contain all the functionalities in the use cases. Also, as the final version, it will also have all the setting about privacy and security, such as whether or not to allow record history. The website will have buttons for introduction of the arthor and reporting bugs. For the interface, on the home page, users can easily and comfortably see all the possibly useful information, such as the recommendation based on the tags or categories they have read before, most popular web page, and highest rating web page. On the top, users can quickly access into their account information and modify each setting. Each web page will have a page itself and display all the information and evaluation of it.

## Project Justification

### Novelty
Now, most of the review aggregation website is for reviewing goods or services and help users to buy them. Although we know that web pages have already taken up the most of the life of people, as a virtual and free unit, people is not going to evaluate or discuss it. They think that the value is just on the good or service, not the basic platform. As a result, my project is going to give people the chance to seek for the value inside the web pages and share the feeling about their experience or advices on the pages. It may not about money, but about interest, knowledge, and life. By using this website, people can not only find the web pages they really need, such as those provided tools or information, but also find some useful but little web pages, such as a novel mini game or a creative communication website. Each one can share the web page they found interesting. Therefore, it is a website for people to explore and communicate about the details in life.

### Complexity
In the project, I think it gives me the chance to show my ability in math and algorithm. In my project, I need to find the way to collect lots of data from users and combine the data to help users. By the data, I can give users advices on what they might want and use all the data to improve the whole website. Moreover, because the website should make users find what they want and get their attention as soon as possible, it needs a complex process, which will take lots of time on designing the home page and each functionality carefully.
