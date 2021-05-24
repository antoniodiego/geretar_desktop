/**
 * Author:  Antônoio Diego <antoniodiegoluz at gmail.com>
 * Created: 22 de mai de 2020
 */

CREATE TABLE notificacoes(
 ID INTEGER IDENTITY,
 data_hora_exibicao DATETIME,
 id_tarefa INTEGER,
 CONSTRAINT rela_tarefa FOREIGN KEY (id_tarefa) REFERENCES tarefas(id)
);