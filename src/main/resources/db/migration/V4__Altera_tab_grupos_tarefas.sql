
/**
 * Author:  Antônoio Diego <antoniodiegoluz at gmail.com>
 * Created: 18 de mai de 2020
 */
ALTER TABLE grupos_tarefas DROP CONSTRAINT FK6DEYU5FMJ2QM7T14PSEOOBT78;
ALTER TABLE tarefas DROP CONSTRAINT FK7SGVAKJ7GTCYH2YLDBKGGPDBQ;
ALTER TABLE usuarios DROP CONSTRAINT FKS4XI9RR0XDQ9RN9547U3XQ3EN;
ALTER TABLE grupos_tarefas ALTER COLUMN id INTEGER IDENTITY;
ALTER TABLE grupos_tarefas ADD CONSTRAINT rela_grupo_pai FOREIGN KEY(pai_id) REFERENCES grupos_tarefas(id);
ALTER TABLE tarefas ADD CONSTRAINT rel_tarefas_pai FOREIGN KEY(pai_id) REFERENCES grupos_tarefas(id);
ALTER TABLE usuarios ADD CONSTRAINT rel_usuario_gr FOREIGN KEY(gruporaiz_id) REFERENCES grupos_tarefas(id);
