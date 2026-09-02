import java.util.Scanner;

public class Main {

    static final int MAX_LIVROS = 100;
    static final int MAX_UTILIZADORES = 100;

    static Scanner scanner = new Scanner(System.in);

    // ==========================================
    // DADOS DOS LIVROS
    // ==========================================

    static int[] idLivros = new int[MAX_LIVROS];
    static String[] titulos = new String[MAX_LIVROS];
    static String[] autores = new String[MAX_LIVROS];
    static int[] anosPublicacao = new int[MAX_LIVROS];
    static int[] quantidades = new int[MAX_LIVROS];
    static int[] totalEmprestimos = new int[MAX_LIVROS];

    // ==========================================
    // DADOS DOS UTILIZADORES
    // ==========================================

    static int[] idUtilizadores = new int[MAX_UTILIZADORES];
    static String[] nomesUtilizadores = new String[MAX_UTILIZADORES];
    static String[] contactosUtilizadores = new String[MAX_UTILIZADORES];

    // ==========================================
    // CONTADORES
    // ==========================================

    static int numeroLivros = 0;
    static int numeroUtilizadores = 0;
    static int numeroTotalEmprestimos = 0;

    // ==========================================
    // MATRIZ DE EMPRÃ‰STIMOS
    // ==========================================

    static int[][] emprestimos =
            new int[MAX_UTILIZADORES][MAX_LIVROS];

    // ==========================================
    // MÃ‰TODO PRINCIPAL
    // ==========================================

    public static void main(String[] args) {

        int opcao;

        do {
            mostrarMenu();

            opcao = lerInteiro("Escolha uma opÃ§Ã£o: ");

            switch (opcao) {

                case 1:
                    registarLivro();
                    break;

                case 2:
                    listarCatalogo();
                    break;

                case 3:
                    pesquisarLivro();
                    break;

                case 4:
                    registarUtilizador();
                    break;

                case 5:
                    listarUtilizadores();
                    break;

                case 6:
                    efectuarEmprestimo();
                    break;

                case 7:
                    registarDevolucao();
                    break;

                case 8:
                    mostrarEstatisticas();
                    break;

                case 9:
                    System.out.println("\nPrograma encerrado.");
                    break;

                default:
                    System.out.println(
                            "\nErro: opÃ§Ã£o invÃ¡lida. " +
                            "Escolha uma opÃ§Ã£o entre 1 e 9."
                    );
            }

        } while (opcao != 9);

        scanner.close();
    }

    // ==========================================
    // MENU PRINCIPAL
    // ==========================================
    
    public static void mostrarMenu() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("          BIBLIOTECA MUNICIPAL");
        System.out.println("==========================================");
        System.out.println("1. Registar livro");
        System.out.println("2. Listar catÃ¡logo");
        System.out.println("3. Pesquisar livro");
        System.out.println("4. Registar utilizador");
        System.out.println("5. Listar utilizadores");
        System.out.println("6. Efectuar emprÃ©stimo");
        System.out.println("7. Registar devoluÃ§Ã£o");
        System.out.println("8. EstatÃ­sticas");
        System.out.println("9. Sair");
        System.out.println("==========================================");
    }

    // ==========================================
    // REGISTO DE LIVROS
    // ==========================================

    public static void registarLivro() {

        System.out.println();
        System.out.println("========== REGISTO DE LIVRO ==========");

        if (numeroLivros >= MAX_LIVROS) {
            System.out.println(
                    "Erro: o limite mÃ¡ximo de livros foi atingido."
            );
            return;
        }

        int id = lerInteiroPositivo("ID do livro: ");

        if (encontrarLivro(id) != -1) {
            System.out.println(
                    "Erro: jÃ¡ existe um livro com o ID " + id + "."
            );
            return;
        }

        String titulo = lerTextoNaoVazio("TÃ­tulo: ");

        String autor = lerTextoNaoVazio("Autor: ");

        int ano = lerInteiroPositivo(
                "Ano de publicaÃ§Ã£o: "
        );

        int quantidade = lerInteiroNaoNegativo(
                "Quantidade disponÃ­vel: "
        );

        idLivros[numeroLivros] = id;
        titulos[numeroLivros] = titulo;
        autores[numeroLivros] = autor;
        anosPublicacao[numeroLivros] = ano;
        quantidades[numeroLivros] = quantidade;
        totalEmprestimos[numeroLivros] = 0;

        numeroLivros++;

        System.out.println();
        System.out.println("Livro registado com sucesso!");
            }
        // ==========================================
    // LISTAR CATÃLOGO
    // ==========================================

    public static void listarCatalogo() {

        System.out.println();
        System.out.println("========== CATÃLOGO DE LIVROS ==========");

        if (numeroLivros == 0) {
            System.out.println(
                    "NÃ£o existem livros registados."
            );
            return;
        }
       for (int i = 0; i < numeroLivros; i++) {
            mostrarDadosLivro(i);
        }
    }

    // ==========================================
    // PESQUISAR LIVRO
    // ==========================================

    public static void pesquisarLivro() {

        System.out.println();
        System.out.println("========== PESQUISAR LIVRO ==========");

        if (numeroLivros == 0) {
            System.out.println(
                    "NÃ£o existem livros registados."
            );
            return;
        }

        System.out.println("1. Pesquisar por tÃ­tulo");
        System.out.println("2. Pesquisar por autor");

        int opcao = lerInteiro(
                "Escolha uma opÃ§Ã£o: "
        );

        if (opcao != 1 && opcao != 2) {
            System.out.println(
                    "Erro: opÃ§Ã£o de pesquisa invÃ¡lida."
            );
            return;
        }

        String termo = lerTextoNaoVazio(
                "Digite o termo de pesquisa: "
        ).toLowerCase();

        boolean encontrado = false;

        for (int i = 0; i < numeroLivros; i++) {

            boolean corresponde;

            if (opcao == 1) {

                corresponde = titulos[i]
                        .toLowerCase()
                        .contains(termo);

            } else {

                corresponde = autores[i]
                        .toLowerCase()
                        .contains(termo);
            }

            if (corresponde) {

                mostrarDadosLivro(i);

                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println(
                    "\nNenhum livro encontrado para: "
                    + termo
            );
        }
    }

    // ==========================================
    // REGISTO DE UTILIZADOR
    // ==========================================

    public static void registarUtilizador() {

        System.out.println();
        System.out.println(
                "======= REGISTO DE UTILIZADOR ======="
        );

        if (numeroUtilizadores >= MAX_UTILIZADORES) {

            System.out.println(
                    "Erro: o limite mÃ¡ximo de utilizadores "
                    + "foi atingido."
            );

            return;
        }

        int id = lerInteiroPositivo(
                "ID do utilizador: "
        );

        if (encontrarUtilizador(id) != -1) {

            System.out.println(
                    "Erro: jÃ¡ existe um utilizador com o ID "
                    + id + "."
            );

            return;
        }

        String nome = lerTextoNaoVazio(
                "Nome: "
        );

        String contacto = lerTextoNaoVazio(
                "Contacto: "
        );

        idUtilizadores[numeroUtilizadores] = id;
        nomesUtilizadores[numeroUtilizadores] = nome;
        contactosUtilizadores[numeroUtilizadores] = contacto;

        numeroUtilizadores++;

        System.out.println();
        System.out.println(
                "Utilizador registado com sucesso!"
        );
    }

    // ==========================================
    // LISTAR UTILIZADORES
    // ==========================================
                    + nomesUtilizadores[indiceUtilizador]
        );

        System.out.println(
                "Quantidade disponÃ­vel: "
                + quantidades[indiceLivro]
        );
    }

    // ==========================================
    // REGISTAR DEVOLUÃ‡ÃƒO
    // ==========================================

    public static void registarDevolucao() {

        System.out.println();

        System.out.println(
                "========== REGISTAR DEVOLUÃ‡ÃƒO =========="
        );

        if (numeroLivros == 0) {

            System.out.println(
                    "Erro: nÃ£o existem livros registados."
            );

            return;
        }

        if (numeroUtilizadores == 0) {

            System.out.println(
                    "Erro: nÃ£o existem utilizadores registados."
            );

            return;
        }

        int idUtilizador = lerInteiroPositivo(
                "ID do utilizador: "
        );

        int indiceUtilizador =
                encontrarUtilizador(idUtilizador);

        if (indiceUtilizador == -1) {

            System.out.println(
                    "Erro: utilizador nÃ£o encontrado."
            );

            return;
        }

        int idLivro = lerInteiroPositivo(
                "ID do livro: "
        );

        int indiceLivro =
                encontrarLivro(idLivro);

        if (indiceLivro == -1) {

            System.out.println(
                    "Erro: livro nÃ£o encontrado."
            );

            return;
        }

        if (emprestimos[indiceUtilizador][indiceLivro] == 0) {

            System.out.println(
                    "Erro: este utilizador nÃ£o possui "
                    + "este livro emprestado."
            );

            return;
        }

        // Remover o emprÃ©stimo da matriz
        emprestimos[indiceUtilizador][indiceLivro] = 0;

        // Devolver o exemplar ao catÃ¡logo
        quantidades[indiceLivro]++;

        System.out.println();
        System.out.println(
                "DevoluÃ§Ã£o registada com sucesso!"
        );

        System.out.println(
                "Livro: " + titulos[indiceLivro]
        );

        System.out.println(
                "Utilizador: "
                + nomesUtilizadores[indiceUtilizador]
        );

        System.out.println(
                "Quantidade disponÃ­vel: "
                + quantidades[indiceLivro]
        );
    }

    // ==========================================
    // ESTATÃSTICAS
    // ==========================================
    public static void mostrarEstatisticas() {

        System.out.println();

        System.out.println(
                "========== ESTATÃSTICAS =========="
        );

        if (numeroLivros == 0) {

            System.out.println(
                    "NÃ£o existem livros registados."
            );

            return;
        }

        System.out.println(
                "Total de tÃ­tulos registados: "
                + numeroLivros
        );

        int totalDisponivel = 0;

        for (int i = 0; i < numeroLivros; i++) {

            totalDisponivel += quantidades[i];
        }

        System.out.println(
                "Total de exemplares disponÃ­veis: "
                + totalDisponivel
        );

        System.out.println(
                "Total de emprÃ©stimos realizados: "
                + numeroTotalEmprestimos
        );

        int indiceMaisEmprestado = 0;

        for (int i = 1; i < numeroLivros; i++) {

            if (totalEmprestimos[i]
                    > totalEmprestimos[indiceMaisEmprestado]) {

                indiceMaisEmprestado = i;
            }
        }

        System.out.println();

        System.out.println(
                "Livro mais emprestado: "
                + titulos[indiceMaisEmprestado]
        );

        System.out.println(
                "Autor: "
                + autores[indiceMaisEmprestado]
        );

        System.out.println(
                "NÃºmero de emprÃ©stimos: "
                + totalEmprestimos[indiceMaisEmprestado]
        );

        System.out.println(
                "=================================="
        );
                }
        // ==========================================
    // PROCURAR LIVRO PELO ID
    // ==========================================

    public static int encontrarLivro(int id) {

        for (int i = 0; i < numeroLivros; i++) {

            if (idLivros[i] == id) {

                return i;
            }
        }

        return -1;
    }

    // ==========================================
    // PROCURAR UTILIZADOR PELO ID
    // ==========================================

    public static int encontrarUtilizador(int id) {

        for (int i = 0; i < numeroUtilizadores; i++) {

            if (idUtilizadores[i] == id) {

                return i;
            }
        }

        return -1;
    }

    // ==========================================
    // MOSTRAR DADOS DE UM LIVRO
    // ==========================================
