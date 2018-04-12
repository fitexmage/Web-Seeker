# Project Proposal

## Project Description
My project is a website which provides a platform for discussion about web pages. It allows users to seek for the web page they want and to share their feeling about the one they like or dislike. Based on the category, users can easily find their web pages by their features. From the basic information provided by others, users can learn more about the web pages. Users can also provide web pages by their own and give rating or review to every web page. 

## Project Design

### Technology
In the project, I will use Maven to carry all the dependencies. Then, I will use Spring MVC as the basic structure and use MySQL as the database. To connect them, I will use JPA as ORM technology, and use Hibernate to use annotation to provide JPA function. To get details from MySQL, I will use Mabatis to implement MySQL command. On the view, I will use CSS and Thymeleaf to make it more compatible and dynamic. I can send the data using Spring MVC to Thymeleaf. To keep the login situation, I need to use session to store the username in the session. The project will deploy with Tomcat. To avoid the block caused by a large population, I will use concurrency. For each request, I will put it into thread, and use "synchronized" or other code on the shared data. Moreover, I may use some searching algorithm to deal with the functionality about searching web pages. Also, I will implement big data technology in the controller to catch every action of users, and use the data to help users and website.

### Use Case
On the website, users can register an account and use it for login. After login, users can search for web pages using the search bar or look for the one they want by choosing categories or tags. Then, users can see the detailed information about the web page, such as its description, pictures, category, tags, rating, and reviews. Here, users can rate the web page and write any reviews they want. On the home page, users can also see the rank of rating of web page which in different categories or tags, and the recommendation about what they may like based on the history. In addition, users can provide the information of a web page by their own. They can create a new web page, such as the name, URL, description, for others to read and rate. If some web pages provide wrong information, users can report it or request to modify it.

## Project Schedule

### Project Milestone 1
Allow users to register and login, allow users to see and modify their account information, allow users to add information for a new web page on the website, allow users to see lists of web pages in the popular page and access into it to read the information.

### Project Milestone 2
Allow users to rate and write reviews for the web pages, allow users to "like" <del>and "dislike"</del> other reviews, store users' exploring history and show them to users, display the latest created web page on the home page, allow users to explore web by categories <del>and tags</del> for users to choose, and then display and the rank of rating of web page.

### Project Milestone 3
Allow users to use search bar to search for name or tags of web pages, allow users to report or request to modify the web pages, allow users to add friends and recommend web pages to their friends, give users recommendation based on history on the homepage.

### Finaly Project
The final project will contain all the functionalities in the use cases. Also, as the final version, it will also have all the setting about preference, such as whether or not to allow record history. The website will have buttons for introduction of the arthor and reporting bugs. For the interface, on the home page, users can easily and comfortably see all the possibly useful information, such as the recommendation based on the tags or categories they have read before, most popular web page, and highest rating web page. On the top, users can quickly access into their account information and modify each setting. Each web page will have a page itself and display all the information and evaluation of it.

## Project Justification

### Novelty
Now, most of the review aggregation website is for reviewing goods or services and help users to buy them. Although we know that web pages have already taken up the most of the life of people, as a virtual and free unit, people is not going to evaluate or discuss it. They think that the value is just on the good or service, not the basic platform. As a result, my project is going to give people the chance to seek for the value of the web pages and share the feeling about their experience or advices on the pages. It may not about money, but about interest, knowledge, and life. Some small web pages may not be known by others, but they are worth to be explored and used. As a result, by using this website, people can get the chance to not only find the web pages they really need, but also find some useful but little web pages, such as a novel mini game or a creative communication website.

### Complexity
In the project, I think it gives me the chance to show my ability in math and algorithm. In my project, I need to find the way to collect lots of data from users and combine the data to help users. By the data, I can give users advices on what they might want and use all the data to improve the whole website. Moreover, because the website should make users find what they want and get their attention as soon as possible, it needs a complex process, which will take lots of time on designing the home page and each functionality carefully.
