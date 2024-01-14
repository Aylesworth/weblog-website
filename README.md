<a name="readme-top"></a>


<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h3 align="center">WeBlog - Blog Website</h3>

  <p align="center">
    A blog website where users can write and share their posts with others
    <br />
    <br />
    <a href="https://github.com/Aylesworth/weblog-website">View Demo</a>
    ·
    <a href="https://github.com/Aylesworth/weblog-website/issues">Report Bug</a>
    ·
    <a href="https://github.com/Aylesworth/weblog-website/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Screen Shot][product-screenshot]]()

Welcome to ***WeBlog***, a vibrant online community where individuals can share their thoughts, experiences, and expertise through personalized blog posts. Our platform is designed to empower users to express themselves freely while fostering meaningful discussions.

**Key Features**:
- ***Login with*** [Google OAuth 2.0](https://developers.google.com/identity/protocols/oauth2)
- ***View posts***:
    - Browse through latest posts by others
    - Search for posts by keyword
    - View posts written by some specific user
	
	
- ***Create own post***
    - Write and share your own post with others
    - Can edit post to keep up with lastest updates
	
- ***Comment on posts***
	- Join and discuss with others on the topic
	- Like and reply to other comments
	

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Angular][angular-badge]][angular-url] [![TypeScript][typescript-badge]][typescript-url]
* [![Spring Boot][spring-badge]][spring-url] [![Java][java-badge]][java-url]
* [![MongoDB][mongodb-badge]][mongodb-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

* [Node.js](https://nodejs.org/en/download) and [npm](https://www.npmjs.com/)

* [JDK 17+](https://www.oracle.com/java/technologies/downloads/) and [Apache Maven](https://maven.apache.org/download.cgi)

* [MongoDB](https://www.mongodb.com/download-center/community/releases)

### Installation

1. Clone the repo

   ```sh
   git clone https://github.com/Aylesworth/weblog-website.git
   ```
2. Start Spring Boot back-end

   ```sh
   cd weblog-spring-boot
   mvn clean install
   ```
3. Start Angular front-end

   ```sh
   npm install -g @angular/cli
   cd weblog-angular
   npm install
   npm install ./ckeditor5
   npm start
   ```
4. Now go to `http://localhost:4200/` on your browser to view the website
   

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Nguyen Duc Anh - [Nguyễn Đức Anh](https://web.facebook.com/nda.2105) - nguyenducanh2105@gmail.com

Project Link: [https://github.com/Aylesworth/game-of-go](https://github.com/Aylesworth/game-of-go)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[product-screenshot]: screenshots/screenshot-01.png
[angular-badge]: https://img.shields.io/badge/angular-%23DD0031.svg?style=for-the-badge&logo=angular&logoColor=white
[angular-url]: https://angular.io/
[typescript-badge]: https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white
[typescript-url]: https://www.typescriptlang.org/
[spring-badge]: https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[spring-url]: https://spring.io/
[java-badge]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[java-url]: https://www.java.com/
[mongodb-badge]: https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white
[mongodb-url]: https://www.mongodb.com/
