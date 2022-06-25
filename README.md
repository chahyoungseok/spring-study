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


## 아쉬운 점
 - MVC 모델의 단점이 명확하게 눈이 띄입니다. View와 Model의 의존도가 정말 높고, 코드가 많아지면 많아질수록 복잡도가 급격히 늘어납니다.
 - form 형식의 데이터 입력은 GET, POST 방식밖에 보낼 수 없어 CRUD에서 UPDATE, DELETE 방식으로 데이터 전달을 하지 못 하였습니다.
