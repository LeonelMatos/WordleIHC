# Para Fazer

<img src="https://www.videogameschronicle.com/files/2022/01/wordle-d.jpg" width="250">

### Github

- [x] Criar repositório
- [ ] Lançar app na Microsoft Store

## Interface

- [x] Resisable
- [x] Multiple views solução

## Perfis

- [x] Criar menu perfis
- [ ] Criar perfis com password
- [ ] Encriptar passwords dos perfis
- [ ] Editar perfis

## Leaderboard

- [ ] Pontuação
- [ ] Ranking (menu)

## GamePlay

- [x] Trocar idioma (francês, inglês, português)
- [x] Níveis de dificuldade (3->7)

## Encriptação

- [ ] Encontrar biblioteca de encriptação

## Idiomas

- [x] Menu no start-view (passado para settings-view)

## Salvar dados

- [ ] Ler/escrever a dificuldade
- [ ] Ler/escrever a linguagem
- [ ] Ler/escrever perfis, pontuação

# Notas

**Bibliotecas de palavras:**
- Collins Scrabble Words?
- CSW19
- [WordleCompetition Github (Inglês) -> 5 letters only](https://github.com/Kinkelin/WordleCompetition/tree/main/data/official)
- [Wiktionary](https://en.wiktionary.org/wiki/Wiktionary:Main_Page)

**Wordlists:**

- Ficheiro (txt ou json):
  - Wordlist com palavras (necessário separar pelos diferentes níveis e idiomas)
  - Necessário encriptar cada palavra da wordlist
- [Dicionário em Java](https://docs.oracle.com/javase/8/docs/api/java/util/Dictionary.html):
  - Palavras são carregadas com a aplicação
  - Não é necessário encriptar

# Recursos

- [Animações JavaFX](https://github.com/iAmGio/animated)
- [Encriptação Cipher](https://www.geeksforgeeks.org/encrypt-and-decrypt-string-file-using-java/)

# Interfaces

## Menu Inicial

**start-view (StartController)**

````
Menu inicial:
                
               (FOTO *1)
     TÍTULO
    
    (JOGAR)(Dificuldade *2)

  (DEFINIÇÕES)
  
     (SAIR)

````
_*1: Imagem do perfil atualmente selecionado. Ao clicar na imagem
abre o menu de perfis._
_*2: Dropdown da dificuldade do jogo (de 3 a 7 letras, ou seja, 5 níveis
de dificuldade)._

## Jogo

**game-view (GameController)**

````
Jogo:
(SAIR)        (IDIOMA)
    |-|-|-|-|
    |-|-|-|-|
    |-|-|-|-|
    
     (CHECK)*1
````
_*1: O check é feito automáticamente após completar a palavra_

```
______________
(X) Idioma
    
   PT EN FR
______________

```

## Definições

**settings-view (SettingsController)**

**profile-view (ProfileController)**

````

````
