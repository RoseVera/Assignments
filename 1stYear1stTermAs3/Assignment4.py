import sys  # GULVERA YAZILITAS    ID: b2210356111

def writing(messageforwriting):  # A writing function for printing the result on output file.
    with open('Battleship.out', 'a') as g:
        g.write(messageforwriting)
        g.write('\n')


try:
    assert len(sys.argv) == 5  # Checking if there are enough arguments on terminal or not.
except AssertionError:
    print('IndexError: Some arguments are missing!')
else:
    controld = []  # I take the wrong file names and add them in a list.
    if sys.argv[1] != 'Player1.txt': controld.append(sys.argv[1])
    if sys.argv[2] != 'Player2.txt': controld.append(sys.argv[2])
    if sys.argv[3] != 'Player1.in': controld.append(sys.argv[3])
    if sys.argv[4] != 'Player2.in': controld.append(sys.argv[4])
    try:   # With this, I try open the all files and if there are error I print the wrong file names above.
        with open(sys.argv[1], 'r') as p1:
            l2 = p1.readlines()
            mainlist1,hiddenlist1 = [], []
            for i in range(100): hiddenlist1.append('-')
            for i in range(len(l2)):
                l2[i] = l2[i].strip('\n')
                u1 = l2[i].split(';')
                for j in range(len(u1)):
                    if len(u1[j]) == 0:
                        u1[j] = '-'
                mainlist1.extend(u1)
        with open(sys.argv[2], 'r') as p2:
            l2 = p2.readlines()
            mainlist2, hiddenlist2 = [], []
            for i in range(100): hiddenlist2.append('-')
            for i in range(len(l2)):
                l2[i] = l2[i].strip('\n')
                u2 = l2[i].split(';')
                for j in range(len(u2)):
                    if len(u2[j]) == 0:
                        u2[j] = '-'
                mainlist2.extend(u2)
        with open(sys.argv[3], 'r') as m1:
            inputs1 = m1.readline()
            inputs1 = inputs1.split(';')
            inputs1.pop()
        with open(sys.argv[4], 'r') as m2:
            inputs2 = m2.readline()
            inputs2 = inputs2.split(';')
            inputs2.pop()
    except Exception:
        if len(controld) == 1:
            print('IOError: input file {} is not reachable.'.format(controld[0]))
            writing('IOError: input file {} is not reachable.'.format(controld[0]))
        elif len(controld) != 1:
            print('IOError: input files '+', '.join(controld)+'are not reachable.')
            writing('IOError: input files '+', '.join(controld)+'are not reachable.')
    else:

        def find_indices(list_to_check, item_to_find):  # I use this function to find all indices that a ship has.
            indices = []
            for idx, value in enumerate(list_to_check):
                if value == item_to_find: indices.append(idx)
            return indices


        def locating_P(mainlist, pl__p_ind):  # To find exact indices of Petrol boat, I wrote an alghorithm.
            preventindexerror = ['-', '-', '-', '-', '-', '-', '-', '-', '-', '-']
            mainlist1forp = mainlist.copy()
            mainlist1forp.extend(preventindexerror)
            p_lerim = []
            for i in range(4):
                a = pl__p_ind[0]
                if mainlist1forp[a+1] == 'P' and mainlist1forp[a+10] != 'P':
                    p_lerim.append([a, a+1])
                    pl__p_ind.remove(a)
                    pl__p_ind.remove(a+1)
                elif mainlist1forp[a+1] != 'P' and mainlist1forp[a+10] == 'P':
                    p_lerim.append([a, a + 10])
                    pl__p_ind.remove(a)
                    pl__p_ind.remove(a + 10)
                elif mainlist1forp[a+1] == 'P' and mainlist1forp[a+10] == 'P':  # Critical elif
                    if mainlist1forp[a+2] == 'P' and mainlist1forp[a+20] != 'P':
                        p_lerim.append([a, a + 10])
                        pl__p_ind.remove(a)
                        pl__p_ind.remove(a + 10)
                    elif mainlist1forp[a+2] != 'P' and mainlist1forp[a+20] == 'P':
                        p_lerim.append([a, a + 1])
                        pl__p_ind.remove(a)
                        pl__p_ind.remove(a+1)
                    elif mainlist1forp[a+2] == 'P' and mainlist1forp[a+20] == 'P':  # Critical elif
                        if mainlist1forp[a+3] == 'P' and mainlist1forp[a+30] != 'P':
                            p_lerim.append([a, a + 1])
                            pl__p_ind.remove(a)
                            pl__p_ind.remove(a+1)
                        elif mainlist1forp[a+3] != 'P' and mainlist1forp[a+30] == 'P':
                            p_lerim.append([a, a + 10])
                            pl__p_ind.remove(a)
                            pl__p_ind.remove(a + 10)
                        elif mainlist1forp[a+3] == 'P' and mainlist1forp[a+30] == 'P':  # Critical elif
                            if mainlist1forp[a+4] == 'P' and mainlist1forp[a+40] != 'P':
                                p_lerim.append([a, a + 10])
                                pl__p_ind.remove(a)
                                pl__p_ind.remove(a + 10)
                            elif mainlist1forp[a+4] != 'P' and mainlist1forp[a+40] == 'P':
                                p_lerim.append([a, a + 1])
                                pl__p_ind.remove(a)
                                pl__p_ind.remove(a+1)
            return p_lerim


        def locating_b(mainlist, pl__b_ind):  # Because we have more than one battleship I wrote a function to find them
            pl__b_ind = [int(k) for k in pl__b_ind]
            a = pl__b_ind[0]
            b1, b2 = [], []  # Main point is I look 1 step right and 1 step down of the first "B" I found.
            if mainlist[a+1] == 'B' and mainlist[a+2] == 'B':
                if mainlist[a+3] == 'B':
                    b1 = pl__b_ind[0:4]
                    b2 = pl__b_ind[4:9]
            elif mainlist[a+10] == 'B' and mainlist[a+20]:
                if mainlist[a+30] == 'B':
                    b1 = [a, a+10, a+20, a+30]
                    pl__b_ind.remove(a)
                    pl__b_ind.remove(a+10)
                    pl__b_ind.remove(a+20)
                    pl__b_ind.remove(a+30)
                    b2 = pl__b_ind
            return b1, b2


        def checking_error(move):  # This part for checking moves errors.
            columnnum = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
            try:
                if move.count(',') < 1: raise IndexError
                args = move.split(',')
                args1, args2 = args[0], args[1]
                if len(args1) == 0 or len(args2) == 0: raise IndexError
                args1 = int(args1)
                try: int(args2)
                except ValueError: pass
                else: raise ValueError
                assert args1 in (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                assert args2 in columnnum
            except IndexError:
                print('IndexError: Your input \'{}\' is invalid. Some arguments are missing.\n'.format(move))
                writing('IndexError: Your input \'' + move + '\' is invalid. Some arguments are missing.\n')
                pass
            except ValueError:
                print('ValueError: Your input \'{}\' is invalid.'.format(move), end='')
                print('First argument must be numeric and, the second must be uppercase letter from(A-J).\n')
                writing('ValueError: Your input \'' + move + '\' is invalid.First argument must be numeric and,'
                 ' the second must be uppercase letter from(A-J).\n')
                pass
            except AssertionError:
                print('AssertionError: Invalid Operation.\n')
                writing('AssertionError: Invalid Operation.\n')
            except Exception:
                print('kaBOOM: run for your life!\n')
                pass
            else: return True  # If there is no error it returns True.


        writing('Battle of Ships Game\n')
        print('Battle of Ships Game\n')
          # Player1's locations of ships. I use my locating functions.
        pl_1_c_ind, pl_1_s_ind = find_indices(mainlist1, 'C'), find_indices(mainlist1, 'S')
        pl_1_d_ind, pl_1_b_ind = find_indices(mainlist1, 'D'), find_indices(mainlist1, 'B')
        pl_1_p_ind = find_indices(mainlist1, 'P')
        plerim1 = locating_P(mainlist1, pl_1_p_ind)
        p1_1, p1_2, p1_3, p1_4 = plerim1[0], plerim1[1], plerim1[2], plerim1[3]
        b1_1, b1_2 = locating_b(mainlist1, pl_1_b_ind)
        dictt1 = {'C':pl_1_c_ind,'D':pl_1_d_ind,'S':pl_1_s_ind,'B1':b1_1,'B2':b1_2,'P1':p1_1,'P2':p1_2,'P3':p1_3,'P4':p1_4}
          # Player2's locations of ships
        pl_2_c_ind, pl_2_s_ind = find_indices(mainlist2, 'C'), find_indices(mainlist2, 'S')
        pl_2_d_ind, pl_2_b_ind = find_indices(mainlist2, 'D'), find_indices(mainlist2, 'B')
        pl_2_p_ind = find_indices(mainlist2, 'P')
        plerim2 = locating_P(mainlist2, pl_2_p_ind)
        p2_1, p2_2, p2_3, p2_4 = plerim2[0], plerim2[1], plerim2[2], plerim2[3]
        b2_1, b2_2 = locating_b(mainlist2, pl_2_b_ind)
        dictt2 = {'C':pl_2_c_ind,'D':pl_2_d_ind,'S':pl_2_s_ind,'B1':b2_1,'B2':b2_2,'P1':p2_1,'P2':p2_2,'P3':p2_3,'P4':p2_4}


        def showinggrids(mainlist1, mainlist2, C1, B1, D1, S1, P1, C2, B2, D2, S2, P2):
            columns = 'A B C D E F G H I J'
            print('Final Information\n')
            writing('Final Information\n')
            print('Player1’s Board                  Player2’s Board')
            writing('Player1’s Board\t\t\t\tPlayer2’s Board')
            print(' ', columns, '           ', columns)
            writing('  ' + columns + '\t\t' + '  ' + columns)
            for i in range(10):
                if i + 1 == 10:
                    k1 = mainlist1[i * 10:i * 10 + 10]
                    str1 = ' '.join([str(elem) for elem in k1])
                    k2 = mainlist2[i * 10:i * 10 + 10]
                    str2 = ' '.join([str(elem) for elem in k2])
                    message = str(i + 1) + str1 + '\t\t' + str(i + 1) + str2
                    print(message)
                    writing(message)
                else:
                    k1 = mainlist1[i * 10:i * 10 + 10]
                    str1 = ' '.join([str(elem) for elem in k1])
                    k2 = mainlist2[i * 10:i * 10 + 10]
                    str2 = ' '.join([str(elem) for elem in k2])
                    message = str(i + 1) + ' ' + str1 + '\t\t' + str(i + 1) + ' ' + str2
                    print(message)
                    writing(message)
            print('\nCarrier', '      ', C1, ' ' * 14, 'Carrier', '      ', C2)
            print('Battleship', '   ', B1, ' ' * 12, 'Battleship', '   ', B2)
            print('Destroyer', '    ', D1, ' ' * 14, 'Destroyer', '    ', D2)
            print('Submarine', '    ', S1, ' ' * 14, 'Submarine', '    ', S2)
            print('Patrol Boat', '  ', P1, ' ' * 8, 'Patrol Boat', '  ', P2, '\n')
            writing('\nCarrier\t\t' + C1 + '\t\t\t\tCarrier\t\t' + C2)
            writing('Battleship\t' + B1 + '\t\t\t\tBattleship\t' + B2)
            writing('Destroyer\t' + D1 + '\t\t\t\tDestroyer\t' + D2)
            writing('Submarine\t' + S1 + '\t\t\t\tSubmarine\t' + S2)
            writing('Patrol Boat\t' + P1 + '\t\t\tPatrol Boat\t' + P2)


        def showinghiddens(hiddenlist1, hiddenlist2, pl, count, move, C1, B1, D1, S1, P1, C2, B2, D2, S2, P2):
            print('Player{}\'s Move\n\nRound : {}\t\t\t Grid Size: 10x10\n'.format(pl, count))
            writing('Player{}’s Move\n\nRound : {}\t\t\t\t\tGrid Size: 10x10\n'.format(pl, count))
            columns = 'A B C D E F G H I J'
            print('Player1’s Hidden Board\t\tPlayer2’s Hidden Board')
            writing('Player1’s Hidden Board\t\tPlayer2’s Hidden Board')
            print(' ', columns, '           ', columns)
            writing('  ' + columns + '\t\t' + '  '+columns)
            for i in range(10):
                if i + 1 == 10:
                    k1 = hiddenlist1[i * 10:i * 10 + 10]
                    str1 = ' '.join([str(elem) for elem in k1])
                    k2 = hiddenlist2[i * 10:i * 10 + 10]
                    str2 = ' '.join([str(elem) for elem in k2])
                    message = str(i + 1) + str1 + '\t\t' + str(i + 1) + str2
                    print(message)
                    writing(message)
                else:
                    k1 = hiddenlist1[i * 10:i * 10 + 10]
                    str1 = ' '.join([str(elem) for elem in k1])
                    k2 = hiddenlist2[i * 10:i * 10 + 10]
                    str2 = ' '.join([str(elem) for elem in k2])
                    message = str(i + 1) + ' ' + str1 + '\t\t' + str(i + 1) + ' ' + str2
                    print(message)
                    writing(message)
            print('\nCarrier', '      ', C1, ' '*14, 'Carrier', '      ', C2)
            print('Battleship', '   ', B1, ' ' * 12, 'Battleship', '   ', B2)
            print('Destroyer', '    ', D1, ' ' * 14, 'Destroyer', '    ', D2)
            print('Submarine', '    ', S1, ' ' * 14, 'Submarine', '    ', S2)
            print('Patrol Boat', '  ', P1, ' ' * 8, 'Patrol Boat', '  ', P2, '\n')
            print('Enter your move: ' + move + '\n')
            writing('\nCarrier\t\t'+C1+'\t\t\t\tCarrier\t\t'+C2)
            writing('Battleship\t'+B1+'\t\t\t\tBattleship\t'+B2)
            writing('Destroyer\t'+D1+'\t\t\t\tDestroyer\t'+D2)
            writing('Submarine\t'+S1+'\t\t\t\tSubmarine\t'+S2)
            writing('Patrol Boat\t'+P1+'\t\t\tPatrol Boat\t'+P2+'\n')
            writing('Enter your move: '+move+'\n')


        def hits(dictt, index, mainlist, hiddenlist):  # I do my shots with this function.
            if mainlist[index] == '-' or mainlist[index] == 'O':
                mainlist[index], hiddenlist[index] = 'O', 'O'
            else:
                mainlist[index], hiddenlist[index] = 'X', 'X'
                for i in dictt.keys():
                    try: dictt.get(i).remove(index)
                    except: pass


        C1, B1, D1, S1, P1, C2, B2, D2, S2, P2 = '-', '- -', '-', '-', '- - - -', '-', '- -', '-', '-', '- - - -'


        def sunkships(dictt, C, B, D, S, P):  # I show which ships are sunk.
            if dictt['C'] == [] : C = 'X'
            if dictt['S'] == [] : S = 'X'
            if dictt['D'] == [] : D = 'X'
            if len(dictt['B1']) == 0 and len(dictt['B2']) == 0: B = 'X X'
            elif bool(len(dictt['B1']) == 0) ^ bool(len(dictt['B2']) == 0): B = 'X -'
            count=0
            for i in ['P1', 'P2', 'P3', 'P4']:
                if dictt[i] == []: count += 1
            if count ==1: P = 'X - - -'
            if count == 2: P = 'X X - -'
            if count == 3: P = 'X X X -'
            if count == 4: P = 'X X X X'
            return C, B, D, S, P


        def winner(dictt1,dictt2):  # Determining winner.
            ships1, ships2, keys1, keys2 = [], [], list(dictt1.keys()), list(dictt2.keys())
            for i in keys1: ships1.extend(dictt1[i])
            for j in keys2: ships2.extend(dictt2[j])
            if ships1 == [] and ships2 == []:
                print('It is a Draw!\n')
                writing('It is a Draw!\n')
            elif ships1 == [] and ships2 != []:
                print('Player2 Wins!\n')
                writing('Player2 Wins!\n')
            elif ships1 != [] and ships2 == []:
                print('Player1 Wins!\n')
                writing('Player1 Wins!\n')


        columnnum = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
        count = 1
        for i in range(len(inputs1)):  # Player1's Move
            player1hamle = inputs1[i]
            if checking_error(player1hamle) != True:  # Checking the move input if it is valid returns True
                continue
            else:
                argstohit = inputs1[i].split(',')
                indextohit2 = (int(argstohit[0])-1) * 10 + int(columnnum.index(argstohit[1]))
                showinghiddens(hiddenlist1, hiddenlist2, 1, count, player1hamle, C1, B1, D1, S1, P1, C2, B2, D2, S2, P2)
                hits(dictt2, indextohit2, mainlist2, hiddenlist2)
                C2, B2, D2, S2, P2 = sunkships(dictt2, C2, B2, D2, S2, P2)
                for j in range(len(inputs2)):  # Player2's Move
                    player2hamle = inputs2[i]
                    if checking_error(player1hamle) != True:  # Checking the move input if it is valid returns True
                        continue
                    else:
                        argstohit = inputs2[i].split(',')
                        indextohit1 = (int(argstohit[0]) - 1) * 10 + int(columnnum.index(argstohit[1]))
                        showinghiddens(hiddenlist1, hiddenlist2, 2, count, player2hamle, C1, B1,D1,S1,P1,C2,B2,D2,S2,P2)
                        hits(dictt1, indextohit1, mainlist1, hiddenlist1)
                        C1, B1, D1, S1, P1 = sunkships(dictt1, C1, B1, D1, S1, P1)
                        count += 1
                        winner(dictt1, dictt2)
                    break
        showinggrids(mainlist1, mainlist2, C1, B1, D1, S1, P1, C2, B2, D2, S2, P2)
