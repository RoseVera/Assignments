from tabulate import tabulate
copy_pl = []  # This list is for the "list command" due to some changes i.e. 0,999--> 99,9%
patientlist = []  # The Main Patient Data List
namelist = []  # This list is our control list. We check the patient's name if they are in there or not.


def writing(messageforwriting):
    with open('doctors_aid_outputs.txt', 'a') as g:
        g.write('\n')
        g.write(messageforwriting)


def create(parameter):
    name = parameter[0]
    if name in namelist:
        message = str('Patient ' + name + ' cannot be recorded due to duplication.')
    else:
        message = str('Patient ' + name + ' is recorded.')
        namelist.append(name)
        patientlist.append(parameter)

    writing(message)  # I use writing function in every function for printing result

    return patientlist


def remove(name):
    name = name.strip()  # I use strip to get rid of blank spaces.
    if name in namelist:
        ind = namelist.index(name)  # I find the index of the name which is required to remove.
        namelist.pop(ind)
        patientlist.pop(ind)
        copy_pl.pop(ind)
        remove_message = 'Patient ' + name + ' is removed.'
        writing(remove_message)
    else:
        remove_message = 'Patient '+name+'cannot be removed due to absence.'
        writing(remove_message)


def list():
    # To have an ordered table I take the second words of headers for tabulate function.
    columns = ['Name', 'Accuracy', 'Name', 'Incedence', 'Name', 'Risk']
    message = tabulate(copy_pl, headers=columns)
    writing(message)


def probability(name):  # probability = disease_incidence/(1-accuracy+disease_incidence)
    name = name.strip()
    if name in namelist:
        ind = namelist.index(name)
        accuracy = float(patientlist[ind][1])  # Taking accuracy argument.
        int_incedence = patientlist[ind][3].strip()
        n, d = map(int, int_incedence.split('/'))  # Taking disease incidence argument.
        disease_inc = n / d
        prob = disease_inc/(1-accuracy+disease_inc)
        prob = (prob.__round__(4))*100
        prob = str(prob)
        cancer_type = patientlist[ind][2]
        message = 'Patient ' + name + ' has a probability of '+prob+' % having '+cancer_type+'.'
        writing(message)

    else:
        message = 'Probability for '+name+' cannot be calculated due to absence.'
        writing(message)


def recommendation(name):  # if treatment risk > prob do not recommend treatment
    name = name.strip()
    if name in namelist:
        ind = namelist.index(name)
        accuracy = float(patientlist[ind][1])
        int_incedence = patientlist[ind][3].strip()
        n, d = map(int, int_incedence.split('/'))
        disease_inc = n / d
        prob = disease_inc / (1 - accuracy + disease_inc)
        prob = (prob.__round__(4)) * 100  # I calculate the probability again for comparing.
        treat_risk = float(patientlist[ind][5].replace('\n', ' '))*100  # Treatment risk
        if treat_risk > prob:
            message = 'System suggests '+name+' NOT to have the treatment.'
            writing(message)
        else:
            message = 'System suggests '+name+' to have the treatment.'
            writing(message)
    else:
        message = 'Recommendation for '+name+' cannot be calculated due to absence.'
        writing(message)


def reading():
    with open('doctors_aid_inputs.txt', 'r') as f:
        lines = f.readlines()  # I read all lines from input and put them in a list for convenience.
        a = len(lines)
        for i in range(a):  # Using "for" for operate every single line and command by order.
            line = lines[i]
            l1 = line.split(' ')
            cmd = l1[0]  # I take the command which is first word in every line.

            if cmd == 'create':
                l1.pop(0)  # Now I take whole line except command and convert it to a list named "l2".
                listtostr = ' '.join([str(elem) for elem in l1])
                l2 = listtostr.split(',')

                l3 = l2.copy()  # Here I arrange some percentage arguments and add arranged form to copy_pl list.
                b = l2[1].strip().replace('.', '')
                if len(b) > 3:
                    l3[1] = b[1:3] + '.' + b[3:] + '%'
                else:
                    l3[1] = b[1:] + '%'
                c = l3[5].replace('\n', '')
                l3[5] = c[3:] + '%'
                copy_pl.append(l3)

                create(l2)  # Calling Create Function.

            elif cmd == 'remove':
                l1.pop(0)
                removename = l1[0]  # I take the name that will be removed.

                remove(removename)

            elif cmd == 'probability':
                l1.pop(0)
                listtostr = ' '.join([str(elem) for elem in l1])
                l2 = listtostr.split(',')
                probname = l2[0]  # I take the name whose probability will be calculated.

                probability(probname)

            elif cmd == 'recommendation':
                l1.pop(0)
                listtostr = ' '.join([str(elem) for elem in l1])
                l2 = listtostr.split(',')
                recname = l2[0]  # I take the name that will be given recommendation.

                recommendation(recname)

            else:  # Else is stand for our last command which is "list".
                table1 = 'Patient  Diagnosis   Disease          Disease      Treatment         Treatment'
                # I choose to write first line of the table by arranging the spaces manually for convenience.
                writing(table1)

                list()  # Calling list function.


reading()  # Finially, I call just the reading function to operate all the input and output.