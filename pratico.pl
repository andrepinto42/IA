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
% ID_pedido | Cliente | Distancia | Encomenda | Hora | Dia | Mes | Ano | Horas para Entregar

pedido('0001',paulo,20,pao,9,25,'abril',2021,72).
pedido('0002',paulo,20,pao,9,25,'abril',2021,3). % Este pedido nao foi entregue
pedido('0003',paulo,20,pc,9,25,'abril',2021,3).
pedido('0004',paulo,20,fones,9,24,'março',2021,3).
pedido('0005',paulo,20,fones,9,25,'março',2021,3).
pedido('0006',toze,20,pc,9,21,'março',2021,3).
pedido('0007',toze,20,fones,9,26,'março',2021,3).
pedido('0008',toze,20,pao,9,25,'abril',2021,3).

% entrega(daniela,toze,5,20,15,carro,pc,25,'abril',2021,3).

% idPedido | Estafeta | Cliente | Veiculo | | Encomenda  | Peso |  Hora | Dia | Mes | Ano | Rating
entrega('0001',joao,paulo,mota,pao,15,10,26,'abril',2021,5).
entrega('0003',joao,paulo,carro,pc,15,10,27,'abril',2021,3).
entrega('0004',joao,paulo,mota,fones,15,10,27,'março',2021,1).
entrega('0005',joao,paulo,bicicleta,15,fones,10,27,'março',2021,4).
entrega('0006',pinto,toze,carro,pc,15,10,27,'março',2021,1).
entrega('0007',pinto,toze,mota,fones,15,10,27,'março',2021,0).
entrega('0008',pinto,toze,pao,bicicleta,15,10,27,'abril',2021,5).

%-----------------------------------------------------------------------------------
%-----------------------------------------------------------------------------------
%
%                                AUXILIARES
%
%-----------------------------------------------------------------------------------
%-----------------------------------------------------------------------------------

tempo(Horas,Dia,Mes,Ano,Tempo) :- mes(Mes,DiasMes), Tempo is Horas + (Dia + DiasMes*30 + Ano * 365) * 24.

foiEntregue(ID1) :- 
	pedido(ID1,_,_,_,Horas1,Dia1,Mes1,Ano1,Prazo),
    entrega(ID1,_,_,_,_,_,Horas2,Dia2,Mes2,Ano2,_),
    tempo(Horas1,Dia1,Mes1,Ano1,Tempo1), % converte para dias
    tempo(Horas2,Dia2,Mes2,Ano2,Tempo2), % converte para dias
    PrazoFinal is Tempo2 -Tempo1,
    write(Prazo),write(' Horas para entregar e entregou ao final de '),write(PrazoFinal),writeln(' horas'),
    Prazo > PrazoFinal,!.
    

%Preco = PrecoEncomenda + Veiculo * Distancia + (DataFim -DataInicio).

precoEntrega(Encomenda, Veiculo,Distancia, Preco) :- 
	veiculo(Veiculo,Rating,_,_),
    encomenda(Encomenda,PrecoEncomenda),
    Preco is PrecoEncomenda + Rating * Distancia. 

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

pesoEstafeta(Estafeta,Dia,Mes,Ano,Peso) :- entrega(_,Estafeta,_,_,_,Peso,_,Dia,Mes,Ano,_).
pesoTotal(Estafeta,Dia,Mes,Ano,P) :- findall(X,pesoEstafeta(Estafeta,Dia,Mes,Ano,X),List), somar_lista(List,P).



%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query9
%• calcular o número de encomendas entregues e não entregues pela Green
%           Distribution, num determinado período de tempo
%
%       nao entregues: nao foram entregues + entregues fora de tempo
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%TODO
entregues(Dia,Mes,Ano,Offset,N) :- 
    foiEntregue(Estafeta,Cliente,Encomenda,Dia,Mes,Ano),
    write(Tempo1),write(' tempo1 e tempo2 = '),writeln(Tempo2),
    N is 1.




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
    entrega(_,Estafeta,_,_,_,_,Horas2,Dia2,Mes2,Ano2,_),
    tempo(0,Dia1,Mes1,Ano1,Tempo1),
    tempo(Horas2,Dia2,Mes2,Ano2,Tempo2),
    Dif is Tempo2-Tempo1,
    Dif >= 0, Offset * 24 > Dif,
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
	entrega(_,_,_,Veiculo,_,_,Horas2,Dia2,Mes2,Ano2,_),
    tempo(0,Dia1,Mes1,Ano1,Tempo1),
    tempo(Horas2,Dia2,Mes2,Ano2,Tempo2),
    Dif is Tempo2-Tempo1,
    Dif >= 0, Offset * 24 > Dif,
    Data is 1.

% idPedido | Estafeta |Cliente   Veiculo  | Encomenda  | Peso |  Hora | Dia | Mes | Ano | Rating


%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query6
%• Calcular a classificação média de satisfação de cliente para um determinado
%                           estafeta
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------


classificMedia(Estafeta,Media) :- findall(X,ratingEstafeta(Estafeta,X),List), tamanho_lista(List,T), somar_lista(List,R), Media is R/T.

ratingEstafeta(Estafeta,Rating) :- entrega(_,Estafeta,_,_,_,_,_,_,_,_,Rating).

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


ruaEntregasCliente(Rua) :- cliente(Cliente,Rua) , entrega(_,_,_,Cliente,_,_,_,_,_,_,_).


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

faturacaoAux(Dia,Mes,Ano,Faturacao) :- entrega(_,_,_,_,Encomenda,_,Dia,Mes,Ano,_),encomenda(Encomenda,Faturacao).



%------------------------------------------------------------------------
%------------------------------------------------------------------------
%   
%                               Query3
%
%• identificar os clientes servidos por um determinado estafeta
%
%------------------------------------------------------------------------
%------------------------------------------------------------------------



listaCliente(Estafeta,List) :- setof(X,qualCliente(Estafeta,X),List).
qualCliente(Estafeta, Cliente) :- entrega(_,Estafeta,Cliente,_,_,_,_,_,_,_).



%-------------------------------------------------------------------------------------------
%-------------------------------------------------------------------------------------------
%
%                                         Query2
%
%• Identificar que estafetas entregaram determinada(s) encomenda(s) a um determinado cliente
%
%-------------------------------------------------------------------------------------------
%-------------------------------------------------------------------------------------------


listaEstafetas(Cliente,Encomenda, List) :- setof(X,qualEstafeta(Cliente,Encomenda,X),List).

qualEstafeta(Cliente,Encomenda, Estafeta) :-  entrega(_,Estafeta,Cliente,Encomenda,_,_,_,_,_,_).





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
    entrega(_,Estafeta,_,Veiculo,_,_,_,_,_,_,_),
    veiculo(Veiculo,Rating,_,_).
                            
