'''# NAME: GULVERA YAZILITAS  ID:b2210356111
import string
import sys
dictionarieslist = []
dictnames = []
columns = []
rows = []


def writing(messageforwriting):  # A writing function for printing the result on output file.
    with open('output.txt', 'a') as g:
        g.write(messageforwriting)
        g.write('\n')


def reading(filename):
    with open(filename, 'r') as f:
        lines = f.readlines()  # I read all lines from input and put them in a list for convenience.
        a = len(lines)
        for i in range(a):  # Using "for" for operate every single line and command by order.
            line = lines[i]
            l1 = line.split(' ')
            cmd = l1[0]  # I take the command which is first word in every line.
            if cmd == 'CREATECATEGORY':
                catname = l1[1]
                r, c = map(int, l1[2].split('x'))  # taking row and column arguments.
                createcategory(catname, r, c)
            elif cmd == 'SELLTICKET':
                cusname = l1[1]
                tickettype = l1[2]
                if tickettype == 'student':
                    ticket = 'S'
                elif tickettype == 'full':
                    ticket = 'F'
                elif tickettype == 'season':
                    ticket = 'T'
                catname = l1[3]
                seat = l1[4:]
                sellticket(cusname, ticket, catname, seat)  # Calling sellticket function
            elif cmd == 'SHOWCATEGORY':
                catname = l1[1]
                catname = catname.strip('\n')
                catind = dictnames.index(catname)
                dictt = dictionarieslist[catind]
                c = columns[catind]
                r = rows[catind]
                letters = string.ascii_uppercase
                letterlist = list(letters[0:r])  # Creating a letter list for proper row.
                letterlist = letterlist[::-1]
                show(dictt, catname, c, letterlist)  # Show function
            elif cmd == 'CANCELTICKET':
                catname = l1[1]
                catname = catname.strip('\n')
                catind = dictnames.index(catname)
                dictt = dictionarieslist[catind]
                seats = l1[2:]
                cancelticket(catname, dictt, seats)
            elif cmd == 'BALANCE':
                catname = l1[1]
                catname = catname.strip('\n')
                catind = dictnames.index(catname)
                dictt = dictionarieslist[catind]
                balance(catname, dictt)


def checkstauts(seat, dictt, cusname, ticket, catname):  # This checks the emptyness of single seats.
    if dictt.get(seat) == 'X':
        dictt.update({seat: ticket})
        print('Success: {} has bought {} at {} '.format(cusname, seat, catname))
        writing('Success: {} has bought {} at {} '.format(cusname, seat, catname))
    else:
        print('Warning: The seat {} cannot be sold to {} since it was already sold!'.format(seat, cusname))
        writing('Warning: The seat {} cannot be sold to {} since it was already sold!'.format(seat, cusname))


def checkrange(seat, dictt, catname, cusname, ticket):  # This checks the range of single seats.
    strletters = ''.join(list(dictt.keys()))
    listnums = set()
    for i in range(len(list(dictt.keys()))):
        listnums.add(list(dictt.keys())[i][1:])
    listnums = list(listnums)
    if (seat[0] not in strletters) and (seat[1:] not in listnums):
        print('Error: The category ’{}’ has less column and row than the specified index {}!'.format(catname, seat))
        writing('Error: The category ’{}’ has less column and row than the specified index {}!'.format(catname, seat))
    elif seat[0] not in strletters:
        print('Error: The category ’{}’ has less row than the specified index {}!'.format(catname, seat))
        writing('Error: The category ’{}’ has less row than the specified index {}!'.format(catname, seat))
    elif str(seat[1:]) not in listnums:
        print('Error: The category ’{}’ has less column than the specified index {}!'.format(catname, seat))
        writing('Error: The category ’{}’ has less column than the specified index {}!'.format(catname, seat))
    else:
        checkstauts(seat, dictt, cusname, ticket, catname)


def checkrangeformultiple(seatset, dictt, catname, seatrange):  # For multiple seats i.e. B3-12
    for seat in seatset:
        strletters = ''.join(list(dictt.keys()))
        listnums = set()
        for i in range(len(list(dictt.keys()))):
            listnums.add(list(dictt.keys())[i][1:])
        if (seat[0] not in strletters) and (seat[1:] not in listnums):
            print('Error: The category ’{}’ has less column and row than the specified index {}!'.format(catname, seatrange))
            writing('Error: The category ’{}’ has less column and row than the specified index {}!'.format(catname, seatrange))
            return False
        elif seat[0] not in strletters:
            print('Error: The category ’{}’ has less row than the specified index {}!'.format(catname, seatrange))
            writing('Error: The category ’{}’ has less row than the specified index {}!'.format(catname, seatrange))
            return False
        elif seat[1:] not in listnums:
            print('Error: The category ’{}’ has less column than the specified index {}!'.format(catname, seatrange))
            writing('Error: The category ’{}’ has less column than the specified index {}!'.format(catname, seatrange))
            return False


def checkstautsformultiple(seatset, dictt, cusname, seatrange):  # For multiple seats i.e. B3-12
    for seat in seatset:
        if dictt.get(seat) != 'X':
            print('Error: The seats {} cannot be sold to {} due some of them have already been sold!'.format(seatrange, cusname))
            writing('Error: The seats {} cannot be sold to {} due some of them have already been sold!'.format(seatrange, cusname))
            return False


def sellticket(cusname, ticket, catname, seat):  # Main sellticket function.
    catind = dictnames.index(catname)            # It has above 4 function to determine if a seat can be sold or not.
    for i in range(len(seat)):
        seat[i] = seat[i].strip('\n')
        if '-' in seat[i]:  # For multiple seats it operates this 'if' block. (i.e. C2-20)
            seatrange = seat[i]
            letter = seat[i][0]
            seat[i] = seat[i][1:]
            fromm, to = seat[i].split('-')
            seatset = []  # Create a seats list from seat range.
            for j in range(int(fromm), int(to)+1):
                seatset.append(letter+str(j))
            availabilityofrange = True
            while availabilityofrange == True:  # Checks every seat's availability for range.
                                                # And returns False when any of the seat is out of range.
                    availabilityofrange = checkrangeformultiple(seatset, dictionarieslist[catind], catname, seatrange)
            if availabilityofrange == None:  # If it returns None , that means there is no obstacle for ranges of seats.
                emptyness = True
                while emptyness == True:  #Does the similar operation for learn the emptyness of seats.
                    emptyness=checkstautsformultiple(seatset, dictionarieslist[catind], cusname, seatrange)
                if emptyness == None:
                    for n in seatset:  # If all seats can be sold, it sells the tickets.
                        dictionarieslist[catind].update({n: ticket})
                    print('Success: {} has bought {} at {}'.format(cusname, seatrange, catname))
                    writing('Success: {} has bought {} at {}'.format(cusname, seatrange, catname))
        else:  # For single seats sell.
            checkrange(seat[i], dictionarieslist[catind], catname, cusname, ticket)


def cancelticket(catname, dictt, seats):
    for seat in seats:
        seat = seat.strip('\n')
        strletters = ''.join(list(dictt.keys()))
        listnums = set()
        for i in range(len(list(dictt.keys()))):
            listnums.add(list(dictt.keys())[i][1:])
        listnums = list(listnums)
        if (seat[0] not in strletters) and (seat[1:] not in listnums):
            print('Error: The category `{}` has less column and row than the specified index {}!'.format(catname, seat))
            writing(
                'Error: The category `{}` has less column and row than the specified index {}!'.format(catname, seat))
        elif seat[0] not in strletters:
            print('Error: The category `{}` has less row than the specified index {}!'.format(catname, seat))
            writing('Error: The category `{}` has less row than the specified index {}!'.format(catname, seat))
        elif str(seat[1:]) not in listnums:
            print('Error: The category `{}` has less column than the specified index {}!'.format(catname, seat))
            writing('Error: The category `{}` has less column than the specified index {}!'.format(catname, seat))
        else:
            if dictt.get(seat) == 'X':
                print('Error: The seat {} at `{}` has already been free! Nothing to cancel'.format(seat, catname))
                writing('Error: The seat {} at `{}` has already been free! Nothing to cancel'.format(seat, catname))
            else:
                dictt.update({seat:'X'})
                print('Success: The seat {} at `{}` has been canceled and now ready to sell again'.format(seat, catname))
                writing('Success: The seat {} at `{}` has been canceled and now ready to sell again'.format(seat, catname))


def createcategory(name, r, c):
    letters = string.ascii_uppercase
    letterlist = list(letters[0:r])
    letterlist = letterlist[::-1]
    dicti = {}
    mykeys = []
    if name in dictnames:
        print('Warning: Cannot create the category for the second time. The stadium has already %s' % name)
        writing('Warning: Cannot create the category for the second time. The stadium has already %s' % name)
    else:
        for i in letterlist:
            for j in range(c):
                mykeys.append(str(i) + str(j))
                dicti = dict.fromkeys(mykeys, 'X')
        dictionarieslist.append(dicti)
        dictnames.append(name)
        columns.append(c)
        rows.append(r)
        seats = str(r*c)
        print('The category ’{}’ having {} seats has been created'.format(name, seats))
        writing('The category ’{}’ having {} seats has been created'.format(name, seats))


def show(dictt, catname, c, letterlist):
    print('Printing category layout of {}\n'.format(catname))
    writing('Printing category layout of {}\n'.format(catname))
    values = list(dictt.values())
    matrix = []
    while len(values) != 0:
        matrix.append(values[:c])
        values = values[c:]
    a = 0
    for i in letterlist:
        matrix[a].insert(0, i)
        a += 1
    for j in range(len(matrix)):
        listtostr = '  '.join([str(elem) for elem in matrix[j]])
        print(listtostr)
        writing(listtostr)
    under = []
    for k in range(c):
        under.append(k)
    if len(str(c-1)) < 2:
        underline = '  '.join([str(elem) for elem in under])
        print('   ' + underline)
        writing('   ' + underline)
    else:
        underline = '  '.join([str(elem) for elem in under[:10]]) + ' ' + ' '.join([str(item) for item in under[10:]])
        print('   ' + underline)
        writing('   ' + underline)


def balance(catname, dictt):
    s = list(dictt.values()).count('S')
    f = list(dictt.values()).count('F')
    t = list(dictt.values()).count('T')
    print('Category report of ’{}’'.format(catname))
    print('-'*32)
    print('Sum of students = {}, Sum of full pay = {}, Sum of season ticket = {}, and Revenues = {} Dollars'.format(s, f, t, s*10+f*20+t*250))
    writing('Category report of ’{}’'.format(catname))
    writing('-'*32)
    writing('Sum of students = {}, Sum of full pay = {}, Sum of season ticket = {}, and Revenues = {} Dollars'.format(s, f, t, s*10+f*20+t*250))


filename = sys.argv[1]
reading(filename)
'''

listt=[]
listt.append(['c','d'])
print(listt)














