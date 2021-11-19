estafeta(joao).
estafeta(pinto).
estafeta(daniela).

%Nome | Residencia
cliente(paulo,'rua dos clerigos').
cliente(toze,'rua dos clerigos').

%Nome | Rating | Peso Maximo | Velocidade Media
veiculo(bicicleta,1,5,10).
veiculo(mota,2,20,35).
veiculo(carro,3,100,25).
% estafeta | cliente | Rating | Distancia | Peso | Veiculo |
entrega(joao,paulo,5,20,15,mota).
entrega(joao,paulo,5,20,15,mota).
entrega(joao,paulo,5,20,15,mota).
entrega(joao,paulo,5,20,15,bicicleta).
entrega(pinto,toze,5,20,15,bicicleta).
entrega(pinto,toze,5,20,15,bicicleta).
entrega(pinto,toze,5,20,15,bicicleta).
entrega(daniela,toze,5,20,15,carro).

rklgmsrpmsrpgxfgbdfxxb


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

ecologicoEstafeta(Estafeta,Rating) :- entrega(Estafeta,_,_,_,_,Veiculo),
                                    veiculo(Veiculo,Rating,_,_).

compareEstafetaEcologic(Estafeta1,Estafeta2,EstafetaFinal) :- 
                                                            ecologicoMain(Estafeta1,Nivel1),
                                                            ecologicoMain(Estafeta2,Nivel2),
                                                            Nivel1>Nivel2 ->EstafetaFinal = Estafeta2;
                                                            EstafetaFinal = Estafeta1.

                            
