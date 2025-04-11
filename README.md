```markdown
# School Management System with Approval Workflow

A Spring Boot application integrated with Flowable workflow engine to manage school entities (Students and Teachers) through an approval process. Managers can review and approve/reject operations like creation, update, or deletion of records.

## Features

- **CRUD Operations for Teachers and Students** with approval workflow.
- **Approval Process** for Student and Teacher operations.
- REST APIs to submit requests and manage tasks.
- BPMN-based workflow for manager approvals.
- Spring Data JPA for database operations.

## Technologies Used

- **Backend**: Spring Boot 3.x
- **Workflow Engine**: Flowable
- **Database**: Spring Data JPA (Configured for H2/MySQL/PostgreSQL)
- **API Documentation**: Spring REST Docs (Optional)

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/LutfuHeysem/SchoolManagementAPIDemo
   cd school-management-system
   ```

2. **Build and Run**
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

3. **Configure Database** (Optional)  
   Update `application.properties` to use your preferred database (e.g., MySQL):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/school_db
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.hibernate.ddl-auto=update
   ```

## API Endpoints

### Student Operations
| Endpoint         | Method | Description                           |
|------------------|--------|---------------------------------------|
| `/students`      | POST   | Submit a new student creation request |
| `/students/{id}` | DELETE | Submit a student deletion request     |
| `/students/{id}` | PATCH  | Submit a student update request       |
| `/students`      | GET    | Retrieve all approved students        |
| `/students/{id}` | GET    | Retrieve a student by ID              |


### Teacher Operations
| Endpoint                 | Method | Description                                  |
|--------------------------|--------|----------------------------------------------|
| `/teachers`              | POST   | Submit a new teacher creation request        |
| `/teachers/{id}`         | DELETE | Submit a teacher deletion request           |
| `/teachers/{id}`         | PATCH  | Submit a teacher update request              |
| `/teachers`              | GET    | Retrieve all approved teachers               |
| `/teachers/{id}`         | GET    | Retrieve a teacher by ID                     |

### Manager Operations
| Endpoint                 | Method | Description                                  |
|--------------------------|--------|----------------------------------------------|
| `/manager/tasks`         | GET    | List pending approval tasks                  |
| `/manager/approve/{id}`  | POST   | Approve a task by ID                         |
| `/manager/reject/{id}`   | POST   | Reject a task by ID                          |

## Workflow Details

1. **Process Flow**:
    - A request (e.g., create/update/delete teacher) triggers the BPMN workflow.
    - Managers review the task via `/manager/tasks`.
    - Decision Gateway routes to approval/rejection based on the `approved` variable.
    - Approved requests persist changes; rejected requests log the action.

2. **BPMN Diagram** (`approval-process.bpmn20.xml`):
   ```
   Start → User Task (Approval Request) → Gateway → [Manager approveService → Service Task] → End
                                       └─── [Manager rejectService → Service Task] ───┘
   ```
![Spring Boot Business Process Demo](https://github.com/user-attachments/assets/ca74224b-fe0e-4a7e-8c12-2477261b3eea)



3. **Delegates**:
    - `ApprovalDelegate`: Handles database operations for approved requests.
    - `RejectionDelegate`: Logs rejected requests.

## Future Improvements

- Add approval status tracking for rejected requests (e.g., set `approvalStatus="REJECTED"`).
- Enhance error handling and validation in APIs.
- Integrate Swagger for API documentation.

I am eager to learn and would love to receive tips on improvement. Thank You!
