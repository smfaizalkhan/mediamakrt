
  ###  Run the project
  
   - clone the project
   - ./mvnw spring-boot:run

 
 ###  Documentation
  - Documentation is on Swagger
    
     http://localhost:8080/swagger-ui.html
 
  ###  Load/Initialize Data
   
    - Excel sheet is converted to "\\t" sepearted values as (*.tsv files) ,stored in calsspath and read in @PostContruct of respective service and feeded to internal H2 database
     
