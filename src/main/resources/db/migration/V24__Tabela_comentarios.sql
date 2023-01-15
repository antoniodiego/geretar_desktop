
/**
 * Author:  antonio
 * Created: 15 de dez de 2020
 */

CREATE TABLE comentarios (
 id INTEGER IDENTITY,
 id_tarefa INTEGER,
 data_comentario DATE,
 hora TIME, 
comentario LONGVARCHAR,
CONSTRAINT rela_coment_tarefa FOREIGN KEY (id_tarefa) REFERENCES tarefas(id)
)