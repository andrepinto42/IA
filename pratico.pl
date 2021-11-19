estafeta(joao).
estafeta(pinto).
estafeta(daniela).

%Nome | Residencia
cliente(paulo,'rua dos clerigos').
cliente(toze,'rua dos clerigos').

encomenda(armario,100).
encomenda(pao,1).
encomenda(pc,3000).
encomenda(fones,50).




%Nome | Rating | Peso Maximo | Velocidade Media
veiculo(bicicleta,1,5,10).
veiculo(mota,2,20,35).
veiculo(carro,3,100,25).
% estafeta | cliente | Rating | Distancia | Peso | Veiculo | Encomenda | data do pedido | data de entrega
entrega(joao,paulo,5,20,15,mota,pao,'25 abril','3 dias').
entrega(joao,paulo,5,20,15,mota,pc, '10 janeiro', '2 fevereiro').
entrega(joao,paulo,5,20,15,mota,fones, '').
entrega(joao,paulo,5,20,15,bicicleta,fones).
entrega(pinto,toze,5,20,15,bicicleta,pc).
entrega(pinto,toze,5,20,15,bicicleta,fones).
entrega(pinto,toze,5,20,15,bicicleta,pao).
entrega(daniela,toze,5,20,15,carro,pc).


entrega(joao,paulo,pao,'26 abril').
%mota = 2 bicicleta =1 carro = 3
%Preco = PrecoEncomenda + Veiculo * Distancia + (DataFim -DataInicio).


%

precoEntrega(Encomenda, Veiculo,Distancia, Preco) :- veiculo(Veiculo,Rating,_,_),
                                                      encomenda(Encomenda,PrecoEncomenda),
                                                      Preco is PrecoEncomenda + Rating * Distancia. 


qualEstafeta(Cliente,Encomenda, Estafeta) :-  entrega(Estafeta,Cliente,_,_,_,_,Encomenda).
listaEstafetas(Cliente,Encomenda, List) :- setof(X,qualEstafeta(Cliente,Encomenda,X),List).

qualCliente(Estafeta, Cliente) :- entrega(Estafeta,Cliente,_,_,_,_,_).
listaCliente(Estafeta,List) :- setof(X,qualCliente(Estafeta,X),List).




%Query1


% myRec/2
myRec( [] , Final ).
myRec( [X|Xs] , Final) :-
  myRec(Xs,X,Final).

% myRec/3 (worker predicate)
myRec( [], Final, Final ).
myRec( [Estafeta1|Xs] , CurrentEstafeta , Final ) :-
  compareEstafetaEcologic(Estafeta1,CurrentEstafeta,NewEstafeta),
  myRec( Xs , NewEstafeta , Final ).


mostEco(Estafeta) :- showEstafetas([H | T]), ecologicRec(T,H,Estafeta).

test([H|T],Estafeta1,Final) :- compareEstafetaEcologic(H,Estafeta1,Final).

ecologicRec([],_,EstafetaFinal) :- writeln(EstafetaFinal).
ecologicRec([H|T],Estafeta,EstafetaFinal) :- 
                                    compareEstafetaEcologic(Head,Estafeta,EstafetaFinal1),
                                    ecologicRec(T,EstafetaFinal1,EstafetaFinal).

showEstafetas(List) :- findall(X,estafeta(X),List).


getAllEstafetas(List) :- findall(Y,estafeta(Y),List).

ecologicoMain(Estafeta,Nivel) :- findall(X,ecologicoEstafeta(Estafeta,X),List),
                                sumlist(List,Tamanho),
                                length(List,TamLista),
                                Nivel is Tamanho / TamLista.

ecologicoEstafeta(Estafeta,Rating) :- entrega(Estafeta,_,_,_,_,Veiculo,_),
                                    veiculo(Veiculo,Rating,_,_).

compareEstafetaEcologic(Estafeta1,Estafeta2,EstafetaFinal) :- 
                                                            ecologicoMain(Estafeta1,Nivel1),
                                                            ecologicoMain(Estafeta2,Nivel2),
                                                            Nivel1>Nivel2 ->EstafetaFinal = Estafeta2;
                                                            EstafetaFinal = Estafeta1.



                            
