Read file: geretar_desktop/pom.xml

Read file: pom.xml
Read file: pom.xml

Read file: README.md

Segue um roteiro rápido para você compilar e executar o Gerenciar Tarefas no Windows.

Pré-requisitos  
1. JDK 8 (ou superior) instalado e no PATH (`java -version` deve responder).  
2. Maven 3.6+ instalado (`mvn -version`).  
3. Git (apenas para clonar, se ainda não fez).

Passo a passo  
1. Abra o PowerShell e vá até a pasta raiz do projeto:  
   ```powershell
   cd F:\Est\Inf\Prog\Proj\Sistemas\Gere tar\Desktop\geretar_desktop
   ```
2. Baixe dependências e gere o JAR “fat” (contendo todas as libs):  
   ```powershell
   mvn clean package -DskipTests
   ```
   • O plugin Shade está configurado no pom.xml; ao final surgirá algo como  
   `target\Gertar_Ma_NB-2.4.15-beta.jar` (ou `*-shaded.jar`, dependendo da versão do Maven).  

3. Execute a aplicação:  
   ```powershell
   java -jar target\Gertar_Ma_NB-2.4.15-beta.jar
   ```
   (Se o nome tiver “-shaded”, use esse nome.)

Alternativas rápidas  
• Execução direta sem empacotar:  
  ```powershell
  mvn compile exec:java \
      -Dexec.mainClass="br.com.antoniodiego.gertarefas.principal.Principal"
  ```  
  (O plugin exec já está pré-configurado; basta rodar o comando acima.)

• IDEs (IntelliJ, Eclipse, NetBeans): importe o projeto como “Maven Project” e rode a classe  
  `br.com.antoniodiego.gertarefas.principal.Principal`.

Observações  
• O projeto usa uma base HSQLDB embutida; não é preciso configurar banco externo.  
• Logs vão para a pasta `logs/` conforme `log4j2.xml`.  
• Caso queira migrar o schema manualmente, há scripts Flyway em `src/main/resources/db/migration/`, mas o Flyway roda automaticamente na primeira execução.  

Pronto! Depois desses passos a janela Swing da aplicação deverá aparecer.