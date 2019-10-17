# Especificações dos Use Cases

## Upload

**Descrição:** O user faz upload de media.

**Cenários:** O Fernando Fósforos faz upload de uma lista de músicas

**Pré-condição:** O user tem conta e o sistema está ativo

**Pós-condição:** O user fez upload e o media center tem a media no sistema

**Fluxo normal:**
1. O user fornece email e password
1. O media center valida a conta
1. O user indica quais media quer fazer upload
1. O sistema pede confirmação se realmente quer fazer upload dos arquivos
1. O user responde que sim
1. O media center recebe os arquivos e coloca-os disponíveis no sistema
1. O user classifica a sua media
1. O user cria uma biblioteca conforme os seus gostos
1. O user utiliza a media que fez upload
1. O user para a media
1. O user faz logout

**Fluxo alternativo 1:** [user ainda não possui conta]

1.1. O user fornece email e password

1.2. O media center recusa o login e mostra o aviso a dizer conta errad aou inexistente 

**Fluxo alternativo 2:** [user certifica-se que a sua conta existe]

2.1. O media center valida a conta

2.2. O user faz logout

**Fluxo alternativo 3:** [user só faz upload de media]

8.1. O user não utiliza a media que fez upload

8.2. O user faz logout

**Fluxo alternativo 4:** [user não confirma o upload]

5.1. O user responde que não

5.2. Regressa ao 2

**Fluxo alternativo 5:** [user não classifica o que fez upload]

7.1. O user não classifica a sua media

7.2. Continua para o 8

**Fluxo alternativo 6:** [user não cria bibliotecas]

8.1 O user não cria bibliotecas

8.2. Continua para o 9

**Fluxo alternativo 7:** [user usa media dos outros users]

9.1. O user utiliza a media que os outros users fizeram upload 

9.2. Continua para o 10


## Download

**Descrição:** O user faz download da sua media

**Cenários:** O Cândido Faíscas faz download de uma lista de músicas que deu upload anteriormente

**Pré-condição:** O user fez upload de media e o sistema está ativo

**Pós-condição:** O user fez download e tem acesso à media offline

**Fluxo normal:** 
1. O user fornece email e password
1. O media center valida a conta
1. O user acede à sua media
1. O user indica qual da sua media quer fazer download
1. O sistema pede confirmação se realmente quer fazer download dos arquivos
1. O user responde que sim
1. O media center fornece os arquivos e o user tem acesso aos mesmos offline
1. O user faz logout

**Fluxo alternativo 1:** [user não possui conta]

1.1. O user fornece email e password 

1.2. O media center recusa o login e mostra o aviso a dizer conta errada ou inexistente

**Fluxo alternativo 2:** [user certifica-se que a sua conta existe]

2.1. O media center valida a conta

2.2. O user faz logout

**Fluxo alternativo 3:** [user não confirma o download]

6.1. O user responde que não

6.2. Regressa ao 3

**Fluxo alternativo 4:** [user tenta fazer download de media dos outros]

3.1. O user acede à media dos outros users

3.2 O user indica qual da media quer fazer download

3.3. O media center recusa o pedido de download

3.4. Regressa ao 2

## Clasificar 

**Descrição:** O user classifica a media que deu  upload

**Cenários:** O Antonio Pechincha  classifica como Rock as músicas que fez upload

**Pré-condição:** O user fez upload de media e o sistema está ativo

**Pós-condição:** A media ficou classificada como o user pretendia

**FLuxo normal:**

1. O user fornece email e password
1. O media center valida a conta
1. O user fez upload de media
1. O user classifica a media que fez upload 
1. O user faz logout

**Fluxo alternativo 1:** [user classifica media que já tinha feito upload]

4.1. O user classifica a media que já tinha feito upload anteriormente

4.2. O user faz logout

**Fluxo alternativo 2:** [user tenta classificar media de outro user]

4.1. O user escolhe media de outro user para classificar

4.2. O media center recusa e mostra um aviso que a media pertence a outro user 

4.3. Regressa ao 4

## Entrar como convidado

**Descrição:** O user utiliza o media center como convidado (sem precisar de conta criada)

**Cenários:** O Crisoprasso Compasso é convidado de um dos residentes da casa e para usufruir do media center, entra como convidado no sistema

**Pré-condição:** O sistema está ativo

**Pós-condição:** O convidado consegue utilizar o sistema sem conta

**Fluxo normal:**

1. O convidado acede ao sistema como convidado
1. O user utiliza a media disponível no servidor
1. O user para a media de que estava a usufruir
1. O user sai do media center

**Fluxo alternativo 1:** [user não utiliza o conteúdo do media center]

2.1. O convidado não utiliza a media disponível no servidor

2.2. O convidado sai do media center

## Criar biblioteca

**Descrição:** O user cria bibliotecas da sua media

**Cenários:** O Nelo Mecânico acede à sua media e cria uma biblioteca ao seu gosto

**Pré-condição:** O user possuir media no sistema

**Pós-condição:** O user fica com a biblioteca feita

**Fluxo normal:** 
1. O user fornece email e password 
1. O media center valida a conta
1. O user acede à sua media
1. O user seleciona a media que deseja fazer biblioteca
1. A biblioteca é criada

## Remover biblioteca

**Descrição:** O user elimina uma ou mais das suas bibliotecas

**Cenário:** A Bernardete Faísca acede à sua lista de bibliotecas e remove as que não lhe faz falta

**Pré-condição:** O user tem pelo menos uma biblioteca na sua lista

**Pós-condição:** O user deixa de ter acesso à biblioteca

**Fluxo normal:**
1. O user fornece email e password 
1. O media center valida a conta
1. O user acede à sua media
1. O user seleciona a biblioteca/bibliotecas que quer remover
1. O sistema pede confirmação se realmente quer remover a biblioteca/bibliotecas
1. O user responde que sim
1. O media center remove a biblioteca

**FLuxo alternativo 1:** [o user não confirma a remoção]

6.1. O user responde que não

6.2. Regressa ao 3

## Login 

**Descrição:** O user entra no sistema com email e password 

**Cenário:** O Fernando Fósforos que tem conta por ser residente da casa entra no sistema usando o seu email e password

**Pós-condição:** O user tem conta criada do sistema

**Pré-condição:** O user consegue efetuar login com sucesso

**Fluxo normal:**
1. O user fornece email e password
1. O media center valida a conta
1. O user efetuou login no sistema

**Fluxo alternativo 1:** [o user não possui conta]

2.1. O media center não valida a conta
2.2. O media center mostra um aviso em como a as credenciais estão erradas ou a conta não existe

## Logout

**Descrição:** O user sai do sistema

**Cenário:** O Cândido Faísca depois de ver o filme sai do sistema para sair de casa

**Pré-condição:** O user já ter feito login no sistema

**Pós-condição:** O user está fora do sistema

**Fluxo normal:** 
1. O user está dentro do sistema
1. O user seleciona a opção de logout para sair do sistema
1. O user está offline e fora do media center

## Media control

**Descrição:** O user consegue controlar a media da qual está a usufruir (play, pause, stop, reverse, fast forward, skip)

**Cenário:** O Fernando Fósforos dá play à sua biblioteca e avança a primeira música por já a ter ouvido várias vezes

**Pré-condição:** O user precisa de media para usar

**Pós-condição:** O user consegue controlar a media conforme pretende

**Fluxo normal:**
1. O user acede à sua media
1. O user dá play de uma das suas músicas
1. O user controla a media usando o pause, reverse, fast forward e skip
1. O user para a media com o stop

## Escolha de media

**Descrição:** O user consegue navegar pela media e filtrar as suas escolhas

**Cenário:** O Nel Chapeiro decide ir à descoberta da media que os outros users fizeram upload, então selecionada o catálogo de media que existe no servidor, escolhe a categoria de músicas e filtra por música Clássica.

**Pré-condição:** O servidor precisa ter media disponível

**Pós-condição:** O user consegue ver e escolher a media que procura

**Fluxo normal:**
1. O user acede à media disponível no servidor
1. O user escolhe a categoria de música
1. O user filtra por bibliotecas
1. O user filtra por uma categoria ou várias categorias à escolha
1. O user escolhe a biblioteca que prefere

**Fluxo alternativo 1:** [o user procura por videos]

2.1. O user escolhe a categoria de vídeos

2.2. Continua para o 3

**Fluxo alternativo 2:** [o user procura por media individual]

3.1. O user filtra por músicas individuais

3.2. Regressa ao 4

**Fluxo alternativo 3:** [o user decide não filtrar]

4.1. O user não filtra a sua procura

4.2. Continua para o 5

## Criar conta

**Descrição:** O user com permissões de administrador cria uma conta usando um email e nome

**Cenário:** A Bernardete Faísca, que possui permissões de administrador, cria a conta do Fernando Fósforos que chegou pela primeira vez a casa

**Pré-condição:** O user que criar a conta possui permissões de administrador

**Pós-condição:** O user para quem a conta foi criada tem acesso para definir a password

**Fluxo normal:** 
1. O user acede à sua conta com permissões de administrador
1. O user seleciona a opção de criar uma nova conta
1. O user insere o email e nome da conta nova
1. O sistema cria a conta nova sem password

## Definir password

**Descrição:** O user para quem foi criada uma conta nova define a sua password e após ter password também a pode redefinir

**Cenário:** O Fernando Fósforos, depois da Bernardete ter criado a sua conta, selecionou a opção de definir password e inseriu o email e nome para a conseguir definir

**Pré-condição:** O user tem uma conta

**Pós-condição:** A password da conta é alterada com sucesso

**Fluxo normal:**
1. O user escolhe a opção de definir password
1. O user insere o email e nome
1. O user escolhe a password e define-a
1. O media center pede confirmação se quer definir a password
1. O user escolhe que sim
1. A password é definida

**Fluxo alternativo 1:** [o user responde que não]

5.1. O user responde que não

5.2. Regressa ao 3

**Fluxo alternativo 2:** [o user redefine a sua password]

1. O user acede à sua conta
1. O user escolhe a opção de redifinir password
1. O user insere a password antiga, escolhe a password nova e redefine
1. O media center pede confirmação se quer redifinir a password
1. O user responde que sim
1. A password é alterada