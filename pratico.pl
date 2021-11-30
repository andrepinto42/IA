estafeta(joao).
estafeta(pinto).
estafeta(daniela).

%Nome | Residencia
cliente(paulo,'rua dos clerigos').
cliente(toze,'rua das maças').

% Nome | Preco
encomenda(armario,100).
encomenda(pao,1).
encomenda(pc,3000).
encomenda(fones,50).

mes('janeiro',1).
mes('fevereiro',2).
mes('março',3).
mes('abril',4).
mes('maio',5).
mes('junho',6).
mes('julho',7).
mes('agosto',8).
mes('setembro',9).
mes('outubro',10).
mes('novembro',11).
mes('dezembro',12).



%Nome | Rating | Peso Maximo | Velocidade Media
veiculo(bicicleta,3,5,10).
veiculo(mota,2,20,35).
veiculo(carro,1,100,25).

data('data1',25,'abril',2021,19,0).
% estafeta | cliente | Rating | Distancia | Peso | Veiculo | Encomenda | Data do pedido | Tempo para Entregar
entrega(joao,paulo,5,20,15,mota,pao,25,'abril',2021,3).
entrega(joao,paulo,5,20,15,mota,pao,25,'abril',2021,3).
entrega(joao,paulo,5,20,15,mota,pc,25,'abril',2021,3).
entrega(joao,toze,5,20,15,mota,fones,24,'março',2021,3).
entrega(joao,toze,5,20,15,bicicleta,fones,25,'março',2021,3).
entrega(pinto,toze,5,20,15,bicicleta,pc,21,'março',2021,3).
entrega(pinto,toze,5,20,15,bicicleta,fones,26,'março',2021,3).
entrega(pinto,toze,5,20,15,bicicleta,pao,25,'abril',2021,3).
% entrega(daniela,toze,5,20,15,carro,pc,25,'abril',2021,3).

% Estafeta | Cliente | Encomenda  | Dia | Mes | Ano | Rating
entrega(joao,paulo,pao,26,'abril',2021,5).
entrega(joao,paulo,pc,27,'abril',2021,3).
entrega(joao,paulo,fones,27,'março',2021,1).
entrega(joao,paulo,fones,27,'março',2021,4).
entrega(pinto,toze,pc,27,'março',2021,1).
entrega(pinto,toze,fones,27,'março',2021,0).
entrega(pinto,toze,pao,27,'abril',2021,5).
entrega(joao,paulo,fones,29,'março',2021,4).

%--------------------------------------------------
%--------------------------------------------------
%             AUXILIARES
%--------------------------------------------------
%--------------------------------------------------

tempo(Dia,Mes,Ano,Tempo) :- mes(Mes,DiasMes), Tempo is Dia + DiasMes*30 + Ano * 365.

foiEntregue(Estafeta,Cliente,Encomenda,Dia1,Mes1,Ano1) :- 
	entrega(Estafeta,Cliente,_,_,_,_,Encomenda,Dia1,Mes1,Ano1,Prazo),
    entrega(Estafeta,Cliente,Encomenda,Dia2,Mes2,Ano2,_),
    tempo(Dia1,Mes1,Ano1,Tempo1),
    tempo(Dia2,Mes2,Ano2,Tempo2),
    PrazoFinal is Tempo2 -Tempo1,
    write(Prazo),write(' e o prazo final é '),write(PrazoFinal),
    Prazo > PrazoFinal,!.



%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query10
%
%• Calcular o peso total transportado por estafeta num determinado dia
%
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------

pesoEstafeta(Estafeta,Dia,Mes,Ano,Peso) :- entrega(Estafeta,_,_,_,Peso,_,_,Dia,Mes,Ano,_).
pesoTotal(Estafeta,Dia,Mes,Ano,P) :- findall(X,pesoEstafeta(Estafeta,Dia,Mes,Ano,X),List), somar_lista(List,P).



%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query9
%• calcular o número de encomendas entregues e não entregues pela Green
%           Distribution, num determinado período de tempo
%
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------




%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query8
%• Identificar o número total de entregas pelos estafetas, num determinado
%						intervalo de tempo
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------

query8(Dia,Mes,Ano,Offset,EntregasTotais) :- findall(X,query8aux(Dia,Mes,Ano,Offset,X),List),sumlist(List,EntregasTotais).

query8aux(Dia1,Mes1,Ano1,Offset,Num) :- 
	estafeta(Estafeta),
	entregasTotalEstafeta(Dia1,Mes1,Ano1,Offset,Estafeta,Num).


entregasTotalEstafeta(Dia1,Mes1,Ano1,Offset,Estafeta,Num) :- 
	findall(Data,dataEntregaEstafeta(Dia1,Mes1,Ano1,Offset,Estafeta,Data),List),
	length(List,Num),
	write(Estafeta),write(' realizou '),write(Num),writeln(' entregas').

dataEntregaEstafeta(Dia1,Mes1,Ano1,Offset,Estafeta,Data) :- 
    entrega(Estafeta,_,_,Dia2,Mes2,Ano2,_),
    tempo(Dia1,Mes1,Ano1,Tempo1),
    tempo(Dia2,Mes2,Ano2,Tempo2),
    Dif is Tempo2-Tempo1,
    Dif >= 0, Offset > Dif,
	Data is 1.




%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query7
%• Identificar o número total de entregas pelos diferentes meios de transporte
%               num determinado intervalo de tempo 
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------

%SO funciona para um offset de Dias
% sumEntregas(List,Num)

query7(Dia,Mes,Ano,Offset,N) :- findall(X,allEntregas(Dia,Mes,Ano,Offset,X),List ), sumlist(List,N).

allEntregas(Dia,Mes,Ano,Offset,List) :-  
	veiculo(Veiculo,_,_,_),
	findall(X,allEntregasVeiculo(Dia,Mes,Ano,Offset,Veiculo,X),List).

allEntregasVeiculo(Dia,Mes,Ano,Offset,Veiculo,Number) :- 
	findall(X,dataEntregaVeiculo(Dia,Mes,Ano,Veiculo,Offset,X),List),length(List,Number),
    write('De '),write(Veiculo),write(' foram feitas estas entregas '),writeln(Number).

dataEntregaVeiculo(Dia1,Mes1,Ano1,Veiculo,Offset,Data) :-
	entrega(Estafeta1,Cliente1,Encomeda1,Dia2,Mes2,Ano2,_),
    entrega(Estafeta1,Cliente1,_,_,_,Veiculo,Encomeda1,_,_,_,_),
    tempo(Dia1,Mes1,Ano1,Tempo1),
    tempo(Dia2,Mes2,Ano2,Tempo2),
    Dif is Tempo2-Tempo1,
    Dif >= 0, Offset > Dif,
    Data is 1.

%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query6
%• Calcular a classificação média de satisfação de cliente para um determinado
%                           estafeta
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------

ratingEstafeta(Estafeta,Rating) :- entrega(Estafeta,_,Rating,_,_,_,_,_,_,_,_).

classificMedia(Estafeta,Media) :- findall(X,ratingEstafeta(Estafeta,X),List), tamanho_lista(List,T), somar_lista(List,R), Media is R/T.

tamanho_lista([],0).
tamanho_lista([_|T],N) :- tamanho_lista(T,N1), N is N1+1.

somar_lista([],0).
somar_lista([H|T],S) :- somar_lista(T,S1), (S is S1+H).



%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query5
%• identificar quais as zonas (e.g., rua ou freguesia) com maior volume de
%  entregas por parte da Green Distribution
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------

%Este processo tem tempo O(N^2 / 2), mas encontra a rua mais comum
ruaCommon(RuaFinal) :- 
	findall(X,ruaEntregasCliente(X),[Head | Tail]),
	ruasRec(Tail,Head,1,NumberVisited,RuaFinal),
    write('A rua mais comum é a '),write(RuaFinal),write(' que foi visitada '),write(NumberVisited),writeln(' vezes').
 
ruasRec([],RuaFinal,Number,Number,RuaFinal).
ruasRec([Head | Tail],Elem,Number,X,RuaFinal) :-
	common( Elem ,[Head | Tail],NumberFinal), 
    (NumberFinal> Number ->
    ruasRec( Tail,Elem,NumberFinal,X,RuaFinal);
    ruasRec( Tail,Elem,Number,X,RuaFinal)
    ).

ruaEntregasCliente(Rua) :- cliente(Cliente,Rua) , entrega(_,Cliente,_,_,_,_,_).

%Retorna o numero de vezes que aparece um determinado elemento em uma lista
common(Elem,List,X) :- common([Elem | List],X).
common([Head | Tail],X) :- numberCommom(Tail,Head,1,X).

numberCommom([],_,NumberFinal,NumberFinal).
numberCommom([Head | Tail],Common,NumberInicial, NumberFinal) :- 
	(Head == Common -> NumberInicial2 is NumberInicial + 1, numberCommom(Tail,Common,NumberInicial2,NumberFinal);
    numberCommom(Tail,Common,NumberInicial,NumberFinal)).



%------------------------------------------------------------------------
%------------------------------------------------------------------------
%   
%                               Query4
%
%• Calcular o valor faturado pela Green Distribution num determinado dia
%------------------------------------------------------------------------
%------------------------------------------------------------------------

faturacao(Dia,Mes,Ano,Faturacao) :- findall(X,faturacaoAux(Dia,Mes,Ano,X),List), sumlist(List,Faturacao).

faturacaoAux(Dia,Mes,Ano,Faturacao) :- entrega(_,_,Encomenda,Dia,Mes,Ano,_),encomenda(Encomenda,Faturacao).



%------------------------------------------------------------------------
%------------------------------------------------------------------------
%   
%                               Query3
%
%• identificar os clientes servidos por um determinado estafeta
%
%------------------------------------------------------------------------
%------------------------------------------------------------------------
%entrega(joao,paulo,5,20,15,mota,pao,25,'abril',2021,3).



listaCliente(Estafeta,List) :- setof(X,qualCliente(Estafeta,X),List).
qualCliente(Estafeta, Cliente) :- entrega(Estafeta,Cliente,_,_,_,_,_).



%-------------------------------------------------------------------------------------------
%-------------------------------------------------------------------------------------------
%
%                                         Query2
%
%• Identificar que estafetas entregaram determinada(s) encomenda(s) a um determinado cliente
%
%-------------------------------------------------------------------------------------------
%-------------------------------------------------------------------------------------------
%Preco = PrecoEncomenda + Veiculo * Distancia + (DataFim -DataInicio).

precoEntrega(Encomenda, Veiculo,Distancia, Preco) :- 
	veiculo(Veiculo,Rating,_,_),
    encomenda(Encomenda,PrecoEncomenda),
    Preco is PrecoEncomenda + Rating * Distancia. 


qualEstafeta(Cliente,Encomenda, Estafeta) :-  entrega(Estafeta,Cliente,_,_,_,_,Encomenda).
listaEstafetas(Cliente,Encomenda, List) :- setof(X,qualEstafeta(Cliente,Encomenda,X),List).





%--------------------------------------------------------------------------------------
%--------------------------------------------------------------------------------------
%
%                                       Query1
%
%• Identificar o estafeta que utilizou mais vezes um meio de transporte mais ecológico
%
%--------------------------------------------------------------------------------------
%--------------------------------------------------------------------------------------

mostEco(Estafeta) :- findall(X,estafeta(X),[H | T]),  mostEcoAux(T,H,Estafeta).

mostEcoAux( [] , _ ).
mostEcoAux( [X|Xs] , Final) :-
  mostEcoAux(Xs,X,Final).

mostEcoAux( [], Final, Final ).
mostEcoAux( [Estafeta1|Xs] , CurrentEstafeta , Final ) :-
  compareEstafetaEcologic(Estafeta1,CurrentEstafeta,NewEstafeta),
  mostEcoAux( Xs , NewEstafeta , Final ).
                               

compareEstafetaEcologic(Estafeta1,Estafeta2,EstafetaFinal) :- 
    ecologicoNivel(Estafeta1,Nivel1),
    ecologicoNivel(Estafeta2,Nivel2),
    Nivel1<Nivel2 ->EstafetaFinal = Estafeta2;
    EstafetaFinal = Estafeta1.

ecologicoNivel(Estafeta,Nivel) :- 
	findall(X,ecologicoEstafeta(Estafeta,X),List),
    sumlist(List,Tamanho),
    length(List,TamLista),
    (TamLista =< 0 -> Nivel is 0;
    Nivel is Tamanho / TamLista).

ecologicoEstafeta(Estafeta,Rating) :- 
	entrega(Estafeta,_,_,_,_,Veiculo,_,_,_,_,_),
    veiculo(Veiculo,Rating,_,_).
                            
