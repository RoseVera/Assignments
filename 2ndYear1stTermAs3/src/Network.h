#ifndef NETWORK_H
#define NETWORK_H

#include <vector>
#include <iostream>
#include "Packet.h"
#include "Client.h"

using namespace std;

class Network {
public:
    Network();
    ~Network();
    int numberOfClients;

    // Executes commands given as a vector of strings while utilizing the remaining arguments.
    void process_commands(vector<Client> &clients, vector<string> &commands, int message_limit, const string &sender_port,
                     const string &receiver_port);

    // Initialize the network from the input files.
    vector<Client> read_clients(string const &filename);
    void read_routing_tables(vector<Client> & clients, string const &filename);
    vector<string> read_commands(const string &filename);

    string findIP(string id, vector<Client> &clients);

    string findMacOfNext(string senderId, string receiverId, vector<Client> &clients);

    string findMacOfSender(string senderId, vector<Client> &clients);

    void printFrameInfo(string inOrOut, string command, vector<string> commandArgv, vector<Client> &clients,
                        const string &sender_port, const string &receiver_port);
};

#endif  // NETWORK_H
