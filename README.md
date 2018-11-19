# TODO LIST Web Application
> 할일 목록(todo-list) 웹 어플리케이션 구현


## 기능
* 사용자는 텍스트로 된 할일을 추가할 수 있다. => O
* 할일 추가 시 다른 할일들을 참조 걸 수 있다. => O
* 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (예시 참고) => O
* 사용자는 할일을 수정할 수 있다. => O
* 사용자는 할일 목록을 조회할 수 있다. => O
* 조회시 작성일, 최종수정일, 내용이 조회 가능하다. => O
* 할일 목록은 페이징 기능이 있다. => O
* 사용자는 할일을 완료처리 할 수 있다. => O
* 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다. (예시 참고) => O


## 요구사항
* 웹 어플리케이션으로 개발 => O
* 웹어플리케이션 개발언어는 Java, Scala, Golang 중 선택을 권장함 (단, 다른 언어에 특별히 자신있는 경우 선택에 제한을 두지 않음) => O(Java)
* 서버는 REST API로 구현 => O
* 프론트엔드 구현방법은 제약 없음 => O(bootstrap 4.1.3)
* 데이타베이스는 사용에 제약 없음 (가능하면 In-memory db 사용) => O(H2) 
* 단위테스트 필수, 통합테스트는 선택  => O
* README.md 파일에 문제해결 전략 및 프로젝트 빌드, 실행 방법 명시  => O

### 목록조회 예시 및 설명
| id | 할일 | 작성일시 | 최종수정일시 | 완료처리 |
|----|-------------|---------------------|----------|---------------------|
| 1 | 집안일 | 2018-04-01 10:00:00 | 2018-04-01 13:00:00 |  |
| 2 | 빨래 @1 | 2018-04-01 11:00:00 | 2018-04-01 11:00:00 |  |
| 3 | 청소 @1 | 2018-04-01 12:00:00 | 2018-04-01 13:00:00 |  |
| 4 | 방청소 @1 @3 | 2018-04-01 12:00:00 | 2018-04-01 13:00:00 |  | 
                        | 1 | 2 | 3 | 4 | 5 |

* 할일 2, 3번은 1번에 참조가 걸린 상태이다.
* 할일 4번은 할일 1, 3번에 참조가 걸린 상태이다.
* 할일 1번은 할일 2번, 3번, 4번이 모두 완료되어야 완료처리가 가능하다.
* 할일 3번은 할일 4번이 완료되어야 완료처리가 가능하다.


## 개발환경
- 프레임워크 : Spring boot 2.1.0
- 언어 : Java 1.8
- IDE : STS(Spring Tool Suite) 3.9.6
- Lombok plugin

## 적용기술
- Frontend 
    - bootstrap 4.1.3   (UI) 
    - fontawesome 5.5.0 (icons)
    - jquery 3.3.1      (Dom control)
    - thymeleaf         (View)
- Backend 
    - springboot 2.1.0  (Framework) 
    - Java 1.8          (Language)
    - REST API          (Interface)
    - H2                (in-memory DB)
    - embeded Tomcat    (WAS)


## 문제 해결 전략
- 짧은시간에 웹어플리케이션을 구성하고, REST API로 개발하기에 가장 적합한 Framework 선택 필요 
    - Springboot 2.1 적용
- Package 구성
    - controller, dao, entity 3가지로만 구성
    - 기능이 복잡하지 않고, 시간절약상의 이유로 service 패키지 구성은 생략
- REST API 구성
    - RESTful 한 URI를 설계
    - GET, POST, DELETE, PUT, PATCH 5개 METHOD 사용
    - /todos 를 기본 URI로 하고, METHOD를 통해 Action 처리의 의미를 부여함
        - / (GET) : SPA 메인화면(mvc view)
        - /todos (GET) : 할일 목록조회(페이징)
        - /todos/all (GET) : 할일 목록조회
        - /todos/{id} (GET) : 할일 상세조회
        - /todos/{id} (DELETE) : 할일 삭제
        - /todos/{id} (PUT) : 할일 수정
        - /todos/{id} (PATCH) : 할일 완료처리    
- 화면전환이 필요 없는 SPA(Single Page Application) 형태로 구현
    - jquery ajax 호출을 통해 비동기 통신
    - JSON 객체로 Response 생성
    - innerHtml 을 통해 dynamic 처리
- Application 중심 설계를 위해 JPA(Java Persistence API) 기술 적용
    - springboot-data-jpa를 이용해 H2 DB와 통신
- '할일'과 '참조ID' 관계 구성을 위해 2개의 Entity를 1:N 관계로 구성
    - ToDo(할일) 
    - ToDoRef(할일참조)
    - @OneToMany 어노테이션을 통해 1:N 관계 설정(외래키)
- 원활한 Pagination을 위해 JPA의 Pageable Parameter 활용
    - 간편하게 페이징 정보가 포함된 JSON 반환 가능
- '할일' 저장 시 '작성일시'와 '최종수정일시'는 Entity 어노테이션을 통해 기능 활용
    - @CreationTimestamp : 생성시간 자동주입
    - @UpdateTimestamp : 수정시간 자동주입('수정' 또는 '완료처리' 이벤트 발생 시에만 update)
- '참조ID' 는 popup창을 통해 기 등록 목록을 선택할 수 있도록 구성
    - 이미 완료처리된 목록은 표출하되, 선택할 수 없도록 처리
- '삭제' 기능은 요구사항에는 없지만, 사용자 편의성 향상을 위해 구현
    - '완료처리' 기능과 비슷하게 참조ID가 존재할 경우 삭제 불가하도록 구현    
    
## 빌드 및 실행 방법
- 터미널 이용 시 
``` bash
./mvnw clean install
./ java -jar target/ToDoListRest-0.0.1-SNAPSHOT.jar

- STS 이용 시
1. git clone (https://github.com/hwasalko/todolistrest.git)
2. Run As -> Spring boot Application
```

- eclipse 이용 시
1. git clone (https://github.com/hwasalko/todolistrest.git)
2. Run As -> Java Application
```