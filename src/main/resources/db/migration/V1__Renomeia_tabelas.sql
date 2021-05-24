/**
 * Author:  Antônoio Diego <antoniodiegoluz at gmail.com>
 * Created: 18 de mai de 2020
 */
ALTER TABLE grupotarefas RENAME TO grupos_tarefas;
ALTER TABLE tarefa RENAME TO tarefas;
ALTER TABLE tarefas ALTER COLUMN DATACONCLUSAO RENAME TO data_conclusao;
ALTER TABLE tarefas ALTER COLUMN DATACRIACAO RENAME TO data_criacao;
ALTER TABLE tarefas ALTER COLUMN DATAFAZER  RENAME TO data_fazer;
ALTER TABLE TAREFACOMPOSTA_TAREFASFILHAS RENAME TO tarefasfilhas_de_comps;
