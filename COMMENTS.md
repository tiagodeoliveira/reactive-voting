### TODO:
* Validar a key do recaptcha no backend
* Ligar o cache do thymeleaf em produção
* Criar um CRUD para cadastrar o paredão, informando o fim do mesmo
* A cobertura de testes ficou fraca, como o desenvolvimento foi exploratório, nem todos os elementos puderam ser desenvolvidos com TDD, melhorar a cobertura, incluindo as classes do react.js (test utils podem ser encontrados https://facebook.github.io/react/docs/test-utils.html)
* Alguns detalhes visuais ainda estão pendentes: a responsividade na tela de resultados por exemplo
* O grafico deve ser ajustado afim de deixar os percentuais da votação de acordo com o do design propost
* O deploy poderia ser automatizando utilizando o Chef/Ansible/etc... ou mesmo orquestrando docker-containers, inicialmente só está copiando o pacote para a maquina host e subindo a aplicação

###REST endpoints:
* /vote/total/ = retorna o total geral de votos até o momento;
* /vote/total/{id do participante} = o total acumulado por participante
* /vote/total/byparticipant = uma lista com o total por participante
* /vote/total/byhour = a média do total de votos por hora até o momento
* /vote/list/{page} = lista os votos ordenados pela data e paginados
* /admin = uma página de administrador com mapping de alguns endpoints do projeto

### Instruções:
* É necessario ter o java8+ instalada na máquina para proceder com a instalação/execução do projeto
* Para a instalação basta rodar *./gradlew run*
* Para empacotar e gerar um arquivo distribuível com dependencias e executaveis: ./gradlew distTar
* O arquivo Dockerfile compila a aplicação, executa os testes, instala na imagem, exclui os fontes e deixa a imagem pronta para ser executada:
  * Construção: *sudo docker build -t tiagodeoliveira .*
  * Execução: *docker run -p 8080:8080 tiagodeoliveira*
* Para fazer a instalação do java8 na distro ubuntu da amazon: http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/
* Em algumas distros que usam strictHostKey no ssh server ocorre um problema de conexão no JSCH, para resolver isso executamos *ssh-keyscan -t rsa server* e adicionamos o retorno no know_hosts
* Para efetuar deploy em novas instancias deve ser adicionada a chave do servidor na pasta deploy-targets/ seguindo o padrão de nomenclatura: usuario-host.pem
* Os resultados do teste de performance podem ser encontrados em src/test/resourcs/perf, estes testes foram realizados com apache benchmark
