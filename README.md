# NetandBack 이슈관리 & 정기점검 시스템 🚀

## 1. 프로젝트 개요 📋
- **프로젝트명**: netandBack 이슈관리 & 정기점검 시스템
- **설명**:  
  netandBack 서비스의 고객사 이슈 접수·처리와 정기점검 스케줄 관리를 위한 백엔드 애플리케이션입니다.
    - **이슈 관리** :  
      고객사에서 발생한 장애, 문의, 기능 요청 등을 등록·조회·수정·삭제하고, 담당자 배정 및 우선순위 설정을 통해  
      내부 개발자 및 엔지니어가 효율적으로 대응할 수 있도록 지원합니다.
    - **정기점검 관리** :  
      고객사·제품별 정기점검 스케줄을 예약하고, 점검 완료 후 결과 보고서를 업로드하며,  
      점검 항목별 결과를 기록하여 이력 관리를 합니다.

---

## 2. 주요 기능 ✨
1. **이슈 관리**
    - **이슈 등록** : 제목, 상세 내용, 고객사, 제품, 이슈 유형, 우선순위, 작성자 정보를 입력하여 신규 이슈 등록
    - **이슈 조회/검색** : 기간(일/주/월), 고객사, 제품, 상태, 우선순위별 필터링 및 페이징 조회
    - **이슈 수정/할당** ✏: 기존 이슈의 상태 변경, 담당자 배정, 우선순위 재설정, 상세 내용 수정
    - **이슈 삭제** : 필요 시 특정 이슈 삭제
    - **담당자 할당 이력** : 담당자가 변경될 때마다 이력이 쌓여 담당자별 처리 내역을 추적할 수 있습니다.

2. **정기점검 관리**
    - **정기점검 스케줄 등록** : 고객사와 제품을 선택하여 점검 일정을 예약하고,  
      점검 상태를 `SCHEDULED`, `COMPLETED`, `CANCELLED`로 관리
    - **정기점검 항목 기록** : 점검 항목별 세부 결과(측정값, 점검 방법, 점검 결과 등)를 입력하여 점검 이력 관리
    - **보고서 업로드** : 점검 완료 후 PDF 형태의 보고서를 첨부하여 보관
    - **정기점검 조회/검색** : 고객사, 제품, 점검일, 상태별로 정기점검 내역 조회

---

## 3. 코드 컨벤션 📏
- **Java 네이버 코드 컨벤션**을 준수합니다.
    - 클래스, 메소드, 변수 네이밍, 들여쓰기, 공백 등 모든 Java 소스 코드는 네이버 Java Style Guide 기반으로 작성합니다.
    - 예시:
      ```java
      // 클래스명: UpperCamelCase
      public class IssueService {
  
          // 메소드명: lowerCamelCase
          public IssueResponse getIssueById(Long issueId) {
              // 들여쓰기: 4칸 스페이스
              if (issueId == null) {
                  throw new IllegalArgumentException("이슈 ID는 null일 수 없습니다.");
              }
              // …
          }
      }
      ```
- **패키지 구조** 🗂️
    - `org.netand.netandback.core` 아래에 공통 모듈(예: annotation, config, error, auth 등)을 위치
    - 도메인별로 `org.netand.netandback.domain.issue`,  
      `org.netand.netandback.domain.inspection` 등으로 구분

---

## 4. 브랜치 전략 🏷️
- **feature 기반 브랜치 전략**을 사용합니다.
    - 신규 기능 개발 시:
      ```bash
      feature/<작업명>
      ```
      예) `feature/issue-crud`, `feature/inspection-schedule`, `feature/api-controller-추가`
    - 기능 개발 완료 → Pull Request → 코드 리뷰 → `develop` 브랜치로 머지
    - `develop` 브랜치를 기준으로 테스트 및 QA를 거쳐, 이상 없으면 `main`(또는 `master`)으로 릴리즈

- **주요 브랜치** 
    - `master`: 프로덕션 릴리즈 버전
    - `develop`: 개발 중간 버전 통합 브랜치
    - `feature/*`: 개별 기능 개발 브랜치
    - `hotfix/*`: 긴급 버그 픽스를 위한 브랜치

---

## 5. 커스텀 애노테이션: `@ApiController` 🔧
- **역할**: `@RestController`와 `@RequestMapping`을 합쳐서 기본 API 경로를 한 번에 지정할 수 있도록 하는 애노테이션
- **사용법** 
    - 클래스 위에 `@ApiController("/api/v1/경로")`만 붙이면, 내부의 `@GetMapping`, `@PostMapping` 등의 메서드는  
      자동으로 `/api/v1/경로`를 기본 URL로 삼습니다.
    - 기존에 매번 `@RestController`, `@RequestMapping`을 반복적으로 사용하던 것을 단 하나의 애노테이션으로 간편하게 대체할 수 있습니다.

---

## 6. 기술 스택 🛠️
- **백엔드**:
    - Spring Boot 3.3.5
    - Java 21
    - Spring MVC
    - Spring Data JPA
    - MySQL
    - Lombok
- **인증/인가**:
    - Spring Security
    - JWT (Access Token은 HTTP 헤더 전달, Refresh Token은 Redis에 저장 후 HttpOnly 쿠키로 관리)
- **빌드 및 의존성 관리**:
    - Gradle
- **문서화**:
    - Swagger(Springdoc), OpenAPI
- **CI/CD**:
    - Jenkins
    - Docker

---
