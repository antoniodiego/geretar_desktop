/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Antônoio Diego <antoniodiegoluz at gmail.com>
 * Created: 29 de jun. de 2020
 */

CREATE TABLE agendamentos(
 ID INTEGER IDENTITY,
 data_agendamento DATETIME,
 id_tarefa INTEGER,
 CONSTRAINT rela_ag_tarefa FOREIGN KEY (id_tarefa) REFERENCES tarefas(id)
);
