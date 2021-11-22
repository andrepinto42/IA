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
entrega(joao,paulo,5,20,15,mota,fones).
entrega(joao,paulo,5,20,15,bicicleta,fones).
entrega(pinto,toze,5,20,15,bicicleta,pc).
entrega(pinto,toze,5,20,15,bicicleta,fones).
entrega(pinto,toze,5,20,15,bicicleta,pao).
% entrega(daniela,toze,5,20,15,carro,pc).


% entrega(joao,paulo,pao,'26 abril').
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



%-----------------------------------------------------
%-----------------------------------------------------
%               Query1
%-----------------------------------------------------
%-----------------------------------------------------

mostEco(Estafeta) :- findall(X,estafeta(X),[H | T]),  mostEcoAux(T,H,Estafeta).

mostEcoAux( [] , Final ).
mostEcoAux( [X|Xs] , Final) :-
  mostEcoAux(Xs,X,Final).

mostEcoAux( [], Final, Final ).
mostEcoAux( [Estafeta1|Xs] , CurrentEstafeta , Final ) :-
  compareEstafetaEcologic(Estafeta1,CurrentEstafeta,NewEstafeta),
  write('Current = '),write(CurrentEstafeta),write(' now is '),writeln( NewEstafeta),
  mostEcoAux( Xs , NewEstafeta , Final ).


ecologicoNivel(Estafeta,Nivel) :- findall(X,ecologicoEstafeta(Estafeta,X),List),
                                sumlist(List,Tamanho),
                                length(List,TamLista),
                                (TamLista == 0 -> TamLista is -1;true),
                                Nivel is Tamanho / TamLista.

compareEstafetaEcologic(Estafeta1,Estafeta2,EstafetaFinal) :- 
                                                            ecologicoNivel(Estafeta1,Nivel1),
                                                            ecologicoNivel(Estafeta2,Nivel2),
                                write('Nivel = '),write(Nivel1),write(' para '),writeln(Estafeta1),
                                write('Nivel = '),write(Nivel2),write(' para '),writeln(Estafeta2),
                                                            (Nivel2 =< 0 -> EstafetaFinal = Estafeta1;true),
                                                            (Nivel1 =< 0 -> EstafetaFinal = Estafeta2;true),
                                                            Nivel1>Nivel2 ->EstafetaFinal = Estafeta2;
                                                            EstafetaFinal = Estafeta1.

ecologicoEstafeta(Estafeta,Rating) :- entrega(Estafeta,_,_,_,_,Veiculo,_),
                                    veiculo(Veiculo,Rating,_,_).
                            
