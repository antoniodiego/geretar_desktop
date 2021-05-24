
/**
 * Author:  Antônoio Diego <antoniodiegoluz at gmail.com>
 * Created: 18 de mai de 2020
 */

ALTER TABLE tarefasfilhas_de_comps ADD CONSTRAINT rel_tarefafilha_mae FOREIGN KEY(TAREFACOMPOSTA_ID) 
REFERENCES tarefas(id);
