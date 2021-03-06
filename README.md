# Spring-Study

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#MVC-패턴">MVC 패턴</a></li>
    <li><a href="#anotation">Anotation</a></li>
    <li><a href="#DTO-Entity">DTO Entity</a></li>
    <li><a href="#Database-접근">Database-접근</a></li>
    <li><a href="#Lombok과-Refactoring">Lombok과 Refactoring</a></li>
    <li><a href="#Data-Create">Data Create</a></li>
    <li><a href="#Data-Read">Data Read</a></li>
    <li><a href="#Data-Update">Data Update</a></li>
    <li><a href="#Data-Delete">Data Delete</a></li>
    <li><a href="#Rest-API">Rest API</a></li>
    <li><a href="#Service">Service</a></li>
    <li><a href="#아쉬운-점">아쉬운 점</a></li>
  </ol>
</details>


## 소개
> spring boot강의를 들으며, 배운 내용을 기록합니다.

<br>

## MVC 패턴

<img src="https://user-images.githubusercontent.com/29851990/175777984-eaa8a06b-6bcc-4012-9151-43237790c5f0.PNG" width="300" height="200"/>

View Templates : 사용자에게 보여지는 웹 페이지입니다. <br>
Model : 데이터를 처리하는 부분입니다. <br>
Controller : 사용자의 입력을 받고 처리하는 부분입니다. <br>


<img src="https://user-images.githubusercontent.com/29851990/175778376-0091dd10-764a-4550-b2d1-69009fe2212e.PNG" width="500" height="100"/>
View Template에 접근하기 위해서는 Controller에서 해당 상황에 필요한 Data를 Model에 넣어 View에 보내게됩니다.

<br>

## DTO Entity


#### DTO
  - View에서 form을 통해 온 Data들은 하나의 객체에 담겨서 오는데 그 객체를 DTO라 합니다.
  - 주로 계층 간 데이터 교환이 이루어질 수 있도록 하는 객체입니다.
  - DTO의 특징으로는 로직을 가지지 않는 순수한 데이터 객체라는 점입니다.
  - 주로 View와 Controller 사이에서 데이터를 주고받을 때 활용성이 높습니다.

#### Entity
  - Entity 또한 로직을 가지지 않는 순수한 데이터 객체입니다.
  - form을 통해 DTO로 온 데이터는 DB에 접근하기 전 Entity로 Casting 되어야 합니다.
  - Entity 객체는 DB의 속성을 담고있어야하며, 각 속성들은 Column Anotation이 있어야 합니다.
  - 또한 private key에 해당하는 GeneratedValue가 있어야합니다.
  - Entity를 DB에서 반환받는 작업을 할 때, 반환값이 null일 수 있으므로 Default Constructor도 설정해줍니다.
  - DTO와 다른점은 DTO는 목적자체가 읽고, 쓰는것이 가능하기 때문에 일회성의 느낌이 강합니다.
  - Entity는 실제 데이터베이스와 관련된 중요한 역할을 하며, 내부적으로 EntityManager에 관리 되기도 합니다.


<br>

## Database 접근
  - h2 DB를 사용하였습니다.
  - application.properties에 "spring.h2.console.enabled=true"를 추가하여 웹 console로 DB에 접근합니다.
  - Controller에서는 CrudRepository를 상속받은 Repository를 사용하여 DB에 접근합니다.

<br>

## Lombok과 Refactoring
build.gradle에 
  > compileOnly 'org.projectlombok:lombok' <br>
	annotationProcessor 'org.projectlombok:lombok'

를 추가합니다.

이는 Constructor, getter, setter, toString, log(Slf4j)등을 Anotation만으로 간단하게 사용할 수 있게 도와줍니다.


<br>

## Data Create
  - View에서 받은 데이터는 DTO를 거쳐 Entity로 Casting 됩니다.
  - CrudRepository를 이용하여 Entity를 save합니다.
  - 이때 CrudRepository를 상속받은 인터페이스는 따로 객체를 만들지 않고, Autowired Anotation을 통해 Spring boot가 미리 생성해놓은 객체를 연결합니다.

<br>

## Data Read
  - CrudRepository를 상속받은 인터페이스를 통해 find하여 Data를 얻습니다. 이때 반환타입은 Iterable입니다.
  - Iterable 타입을 수정하고 싶다면 CrudRepository를 상속받은 인터페이스로가 CrudRepository에 find 메서드들을 오버라이딩을 하여 원하는 타입으로 바꿔줍니다.
  - 해당 프로젝트에선 ArrayList로 오버라이딩 반환타입을 수정했고, List의 하위타입인 ArrayList의 특성을 고려하여 List 타입으로 데이터를 받았습니다.
  - 가져온 데이터는 Model에 등록하여 View에 전달하였고, {{#전달한 데이터}} {{/전달한 데이터}}와 같이 범위를 지정하여 데이터를 사용했습니다.

<br>

## Data Update
  - Data Create를 할 때, 사용하였던 save는 save할 Entity의 id가 이미 DB에 있다면 갱신을 하는 역할을 합니다.

<br>

## Data Delete
  - 해당하는 id를 DB에서 찾아 Entity를 반환받고, 반환받은 Entity를 통해 Delete합니다.
  - RedirectAttributes을 통해 Flash한 데이터를 추가하고, redirect되는 View에 alert로 메시지를 띄워줄 수 도 있습니다.

<br>

## Rest API
  - 다양한 Client들이 나오면서 브라우저는 그에 맞는 적절한 응답을 해주어야합니다. 이를 해결하기위해 Rest Api가 등장하였습니다.
  - Rest Api란 웹서버의 자원을 클라이언트에게 구애받지 않고 사용할 수 있게 만드는 설계방식
  - Http를 통해 서버의 자원을 다루게하는 기술
  - 이때 서버는 응답으로 View가 아닌 Data(json)만을 전달합니다. 
  - 따라서 ApiController라는 Controller를 새로 만들어 기존 View Template을 반환하던 Controller와는 다르게 Data만을 반환하는 코드를 작성합니다.
  - 추가) spring boot 2.7.0 에서 Unique index or primary key violation 에러가 생겼습니다. build.gradle 에서 2.6.7 로 낮추니 동작합니다.
  - html form에서는 GET, POST 방식밖에 전송하지 못하지만 Rest api를 설계하므로 GET, POST, PATCH, DELETE 방식의 전송처리도 하였습니다.
  - 반환타입은 Data이고 ResponseEntity를 통해 200, 400등 상태를 반환하였습니다.
 
<br>

## Service
  - Service는 Controller와 Repository 사이에서 처리업무의 순서를 결정하는 역할을 합니다.
  - 이때 Service의 처리업무는 Transaction 단위로 이루어집니다.
  - Transaction이란 모두 성공되어야하는 일련의 과정을 말합니다.
  - 만일 실패를 한다면 진행초기단계로 돌리는데 이를 Rollback이라 합니다.
  - 따라서 Controller에 있던 코드를 Service를 만들어 분업화 합니다.
  - Controller안에서 Repository에 접근한다면 그것은 Service코드로 Refactoring합니다.
  - Service에서 Transaction을 처리하는 메서드에 @Transactional Anotation을 설정해주면 중간에 에러가 나도 Rollback 시켜줍니다.

<br>

## 아쉬운 점
 - MVC 모델의 단점이 명확하게 눈이 띄입니다. View와 Model의 의존도가 정말 높고, 코드가 많아지면 많아질수록 복잡도가 급격히 늘어납니다.
