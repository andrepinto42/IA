estafeta(joao).
estafeta(pinto).
estafeta(daniela).

%Nome | Residencia
cliente(paulo,'rua dos clerigos').
cliente(toze,'rua dos clerigos').

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
veiculo(bicicleta,1,5,10).
veiculo(mota,2,20,35).
veiculo(carro,3,100,25).
% estafeta | cliente | Rating | Distancia | Peso | Veiculo | Encomenda | Data do pedido | Tempo para Entregar
entrega(joao,paulo,5,20,15,mota,pao,25,'abril',2021,3).
entrega(joao,paulo,5,20,15,mota,pc,25,'abril',2021,3).
entrega(joao,paulo,5,20,15,mota,fones,24,'março',2021,3).
entrega(joao,paulo,5,20,15,bicicleta,fones,25,'março',2021,3).
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
%--------------------------------------------------
%--------------------------------------------------
%             AUXILIARES
%--------------------------------------------------
%--------------------------------------------------

foiEntregue(Estafeta,Cliente,Encomenda,Dia1,Mes1,Ano1) :- entrega(Estafeta,Cliente,_,_,_,_,Encomenda,Dia1,Mes1,Ano1,Prazo),
                                                        entrega(Estafeta,Cliente,Encomenda,Dia2,Mes2,Ano2,_),
                                                        mes(Mes1,DiasMes1),
                                                        mes(Mes2,DiasMes2),
                                                        Tempo1 is Dia1 + DiasMes1*30 + Ano1 * 365,
                                                        Tempo2 is  Dia2 + DiasMes2*30 + Ano2 * 365,
                                                        PrazoFinal is Tempo2 -Tempo1,
                                                        write(Prazo),write(' e o prazo final é '),write(PrazoFinal),
                                                        Prazo > PrazoFinal,!.
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------
%   
%                               Query7
%• Identificar o número total de entregas pelos diferentes meios de transporte
%  num determinado intervalo de tempo;
%
%------------------------------------------------------------------------------
%------------------------------------------------------------------------------

totalEntregasTempo(Dia,Mes,Ano,Tempo,Veiculo,NumeroTotal) :- entrega(Estafeta,Cliente,Encomenda,_,_,_,_,_,_,_,_).


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

precoEntrega(Encomenda, Veiculo,Distancia, Preco) :- veiculo(Veiculo,Rating,_,_),
                                                      encomenda(Encomenda,PrecoEncomenda),
                                                      Preco is PrecoEncomenda + Rating * Distancia. 


qualEstafeta(Cliente,Encomenda, Estafeta) :-  entrega(Estafeta,Cliente,_,_,_,_,Encomenda).
listaEstafetas(Cliente,Encomenda, List) :- setof(X,qualEstafeta(Cliente,Encomenda,X),List).

listaCliente(Estafeta,List) :- setof(X,qualCliente(Estafeta,X),List).
qualCliente(Estafeta, Cliente) :- entrega(Estafeta,Cliente,_,_,_,_,_).



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
  write('Current = '),write(CurrentEstafeta),write(' now is '),writeln( NewEstafeta),
  mostEcoAux( Xs , NewEstafeta , Final ).
                               

compareEstafetaEcologic(Estafeta1,Estafeta2,EstafetaFinal) :- 
                                                            ecologicoNivel(Estafeta1,Nivel1),
                                                            ecologicoNivel(Estafeta2,Nivel2),
                                write('Nivel = '),write(Nivel1),write(' para '),writeln(Estafeta1),
                                write('Nivel = '),write(Nivel2),write(' para '),writeln(Estafeta2),
                                                            (Nivel2 =< 0 -> EstafetaFinal = Estafeta1;true),
                                                            (Nivel1 =< 0 -> EstafetaFinal = Estafeta2;true),
                                                            Nivel1>Nivel2 ->EstafetaFinal = Estafeta2;
                                                            EstafetaFinal = Estafeta1.

ecologicoNivel(Estafeta,Nivel) :- findall(X,ecologicoEstafeta(Estafeta,X),List),
                                sumlist(List,Tamanho),
                                length(List,TamLista),
                                TamLista >0,
                                Nivel is Tamanho / TamLista.

ecologicoEstafeta(Estafeta,Rating) :- entrega(Estafeta,_,_,_,_,Veiculo,_,_,_,_,_),
                                    veiculo(Veiculo,Rating,_,_).
                            
