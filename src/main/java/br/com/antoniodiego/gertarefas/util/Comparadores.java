package br.com.antoniodiego.gertarefas.util;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;

public class Comparadores {

    private static final Logger logComparador = LoggerFactory.getLogger(Comparadores.class);

    /**
     * Para ordenar tarefas por ord de prio
     */
    public static class ComparaTarPrio implements Comparator<Tarefa> {

        @Override
        public int compare(Tarefa tar1, Tarefa tar2) {

            // System.out.println("comp: " + tar1.getTitulo() + " " + tar1.getPrioridade() +
            // " " + tar2.getTitulo() + " " + tar2.getPrioridade());

            /*
             * Os itens considerados maiores ficam no fim da lista e os menores no início,
             * por isso deve ser bom que os de maiores prioridades devem ser considerados
             * menores
             */
            return tar2.getPrioridade().compareTo(tar1.getPrioridade());
        }
    }

    /**
     * Para ordenar tarefas por ord de prio
     */
    public static class ComparaGruposPrio implements Comparator<GrupoTarefas> {

        @Override
        public int compare(GrupoTarefas g1, GrupoTarefas g2) {
            System.out.println("comp: " + g1.getTitulo() + " " + g1.getTitulo());
            /*
             * Quais devem ser as estratégias para efetuar a ordenação dos grupos de acordo
             * com prioridades? Quais são meus desejos? Desejo que os na parte de cima
             * fiquem grupos que contenham tarefas de maior prioridade, mas parece que seria
             * ainda mais eficiente se os gupos cujas tarefas mais prio tive mesmo valor,
             * ficasse no topo os que tivesse mais de uma dela.
             */
 /*
             * Os itens considerados maiores ficam no fim da lista e os menores no início,
             * por isso deve ser bom que os de maiores prioridades devem ser considerados
             * menores
             */
            int retorno = 0;

            // Deve ser necessário comparar as tarefas de cada grupo
            List<Tarefa> tarefaG1 = g1.getTarefas();
            List<Tarefa> tarefaG2 = g2.getTarefas();

            Tarefa prioG1 = null;
            Tarefa prioG2 = null;

            if (tarefaG1.isEmpty() && tarefaG2.isEmpty()) {
                logComparador.info("Dois grupos sem tarefas.");
                logComparador.trace("Proc nos filhos.");

                /*
                 * Percebi que retornar zero aqui pode ser ruim. Parece que o certo seria ver se
                 * tem tarefa em algum sub -grupo do grupo, e assim por diante, em cada grupo o
                 * valor dele seria corr à tarefa de maior prio que fosse encontrada.
                 */

 /*
                 * Aqui parece que, se os subgrupos tivessem ordenados esse algo funcionaria
                 * melhor, pois as de maior pri est nos prim grupos
                 */
                List<GrupoTarefas> gruposG1 = g1.getSubgrupos();
                List<GrupoTarefas> gruposG2 = g1.getSubgrupos();

                if (!gruposG1.isEmpty() && !gruposG2.isEmpty()) {
                    // Busca

                    for (GrupoTarefas grupoG1 : gruposG1) {
                        if (!grupoG1.getTarefas().isEmpty()) {
                            prioG1 = grupoG1.getTarefas().get(0);
                            break;
                        } else {
                            prioG1 = procuraPrimTarefa(grupoG1);
                            break;
                        }
                    }

                    for (GrupoTarefas grupoG2 : gruposG2) {
                        if (!grupoG2.getTarefas().isEmpty()) {
                            prioG2 = grupoG2.getTarefas().get(0);
                            break;
                        } else {
                            prioG2 = procuraPrimTarefa(grupoG2);
                            break;
                        }
                    }

                    /*
                     * Busca por taref de sub term
                     */
                }

            }

            if (tarefaG1.isEmpty()) {
                logComparador.info("Prim sem tarefas");
                // Aqui dev ser proc nos filh

                prioG1 = procuraPrimTarefa(g1);
            } else if (tarefaG2.isEmpty()) {
                prioG2 = procuraPrimTarefa(g2);
            } else {
                // Deve ser necessário obter-se as tarefas mais prioritarias de cada um.
                // Inicialmente prentendo supor que elas já estão ordena por essa qual
                prioG1 = tarefaG1.get(0);
                prioG2 = tarefaG2.get(0);
            }

            if (prioG1 == null && prioG2 == null) {
                return 0;
            }

            if (prioG1 != null && prioG2 == null) {

                return -1;
            }

            if (prioG1 == null && prioG2 != null) {
                return 1;
            }

            if (prioG1.getPrioridade() > prioG2.getPrioridade()) {
                // Grupo 1 deve ser menor
                return -1;
            } else if (prioG1.getPrioridade() < prioG2.getPrioridade()) {
                // Grupo 1 deve ser menor
                retorno = 1;
            } else {
                retorno = 0;
            }

            return retorno;
        }

        /**
         *
         * @param grupo
         * @return
         */
        private Tarefa procuraPrimTarefa(GrupoTarefas grupo) {
            logComparador.info("Proc tar em " + grupo.getTitulo());
            Tarefa tarEnc = null;
            if (!grupo.getTarefas().isEmpty()) {
                tarEnc = grupo.getTarefas().get(0);
            } else {
                // Será nec proc em subg
                List<GrupoTarefas> grupos = grupo.getSubgrupos();
                for (GrupoTarefas sG : grupos) {
                    tarEnc = procuraPrimTarefa(sG);
                    break;
                }
            }
            logComparador.info("Tar enc em " + grupo.getTitulo() + " : " + tarEnc);
            return tarEnc;
        }

    }

}
