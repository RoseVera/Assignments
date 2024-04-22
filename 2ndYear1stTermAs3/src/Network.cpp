#include "Network.h"

Network::Network() {

}
std::string Network::findIP(std::string id,vector<Client> &clients){
    for (auto & client : clients) {
        if(client.client_id==id){
            return client.client_ip;
        }
    }
}

std::string Network::findMacOfNext(std::string senderId,std::string receiverId,vector<Client> &clients){
    for (int i = 0; i < clients.size(); ++i) {
        if(clients[i].client_id==senderId){
            for (const auto& pair : clients[i].routing_table) {
                if(pair.first==receiverId){
                    for (auto & client : clients) {
                        if(client.client_id==pair.second){
                            return client.client_mac;
                        }
                    }
                    return "drop";
                }
            }
        }
    }
}

std::string Network::findMacOfSender(std::string senderId,vector<Client> &clients){
    for (auto & client : clients) {
        if(client.client_id==senderId){
            return client.client_mac;
        }
    }
}

void Network::printFrameInfo(std::string inOrOut,std::string command,std::vector<string> commandArgv,vector<Client> &clients,const string &sender_port, const string &receiver_port) {
    std::string client = commandArgv[1];
    std::string whichQueue = commandArgv[2];
    std::string whichFrame = commandArgv[3];
    int frameNumber = stoi(whichFrame);
    std::string title = "Command: ";
    title += command;
    std::cout << std::string(title.size(), '-') << endl << title << endl << std::string(title.size(), '-') << endl;
    for (auto isItValid:clients) {
        if(isItValid.client_id==client){
            if ((inOrOut=="out"&&isItValid.outgoing_queue.size()<frameNumber) || ((inOrOut=="in"&&isItValid.incoming_queue.size()<frameNumber))) {
                std::cout<<"No such frame.\n";
            }
            else {
                if (inOrOut == "out") {
                    std::cout << "Current Frame #" << whichFrame << " on the outgoing queue of client " << client << endl;
                    for (auto client2: clients) {
                        if (client2.client_id == client) {
                            std::queue<std::stack<Packet *>> tempQueue;
                            std::stack<Packet *> frame;
                            int i = 0;
                            while (!client2.outgoing_queue.empty()) {
                                tempQueue.push(client2.outgoing_queue.front());
                                if (i == frameNumber - 1) { frame = client2.outgoing_queue.front(); }
                                client2.outgoing_queue.pop();
                                i++;
                            }
                            client2.outgoing_queue = tempQueue;

                            PhysicalLayerPacket *phyPacket = nullptr;
                            TransportLayerPacket *traPacket = nullptr;
                            NetworkLayerPacket *netPacket = nullptr;
                            ApplicationLayerPacket *appPacket = nullptr;
                            std::stack<Packet *> tempStack;

                            int k = 0;
                            while (!frame.empty()) {
                                tempStack.push(frame.top());
                                if (k == 0) { phyPacket = dynamic_cast<PhysicalLayerPacket *>(frame.top()); }
                                else if (k == 1) { netPacket = dynamic_cast<NetworkLayerPacket *>(frame.top()); }
                                else if (k == 2) { traPacket = dynamic_cast<TransportLayerPacket *>(frame.top()); }
                                else if (k == 3) { appPacket = dynamic_cast<ApplicationLayerPacket *>(frame.top()); }
                                frame.pop();
                                k++;
                            }
                            for (int j = 0; j < 4; ++j) {
                                Packet *tempPacket = tempStack.top();
                                frame.push(tempPacket);
                                tempStack.pop();
                            }
                            std::string message = appPacket->message_data;
                            int hops = phyPacket->number_of_hops;
                            std::cout << "Carried Message: \"" << message << "\"" << endl;
                            std::cout << "Layer 0 info: ";
                            appPacket->print();
                            std::cout << "Layer 1 info: ";
                            traPacket->print();
                            std::cout << "Layer 2 info: ";
                            netPacket->print();
                            std::cout << "Layer 3 info: ";
                            phyPacket->print();
                            std::cout << "Number of hops so far: " << hops << endl;
                        }
                    }
                } else if (inOrOut == "in") {
                    std::cout << "Current Frame #" << whichFrame << " on the incoming queue of client " << client << endl;
                    for (auto client2: clients) {
                        if (client2.client_id == client) {
                            std::queue<std::stack<Packet *>> tempQueue;
                            std::stack<Packet *> frame;
                            int i = 0;
                            while (!client2.incoming_queue.empty()) {
                                tempQueue.push(client2.incoming_queue.front());
                                if (i == frameNumber - 1) { frame = client2.incoming_queue.front(); }
                                client2.incoming_queue.pop();
                                i++;
                            }
                            client2.incoming_queue = tempQueue;

                            PhysicalLayerPacket *phyPacket = nullptr;
                            TransportLayerPacket *traPacket = nullptr;
                            NetworkLayerPacket *netPacket = nullptr;
                            ApplicationLayerPacket *appPacket = nullptr;
                            std::stack<Packet *> tempStack;

                            int k = 0;
                            while (!frame.empty()) {
                                tempStack.push(frame.top());
                                if (k == 0) { phyPacket = dynamic_cast<PhysicalLayerPacket *>(frame.top()); }
                                else if (k == 1) { netPacket = dynamic_cast<NetworkLayerPacket *>(frame.top()); }
                                else if (k == 2) { traPacket = dynamic_cast<TransportLayerPacket *>(frame.top()); }
                                else if (k == 3) { appPacket = dynamic_cast<ApplicationLayerPacket *>(frame.top()); }
                                frame.pop();
                                k++;
                            }
                            for (int j = 0; j < 4; ++j) {
                                Packet *tempPacket = tempStack.top();
                                frame.push(tempPacket);
                                tempStack.pop();
                            }
                            std::string message = appPacket->message_data;
                            int hops = phyPacket->number_of_hops;
                            std::cout << "Carried Messge: \"" << message << "\"" << endl;
                            std::cout << "Layer 0 info: ";
                            appPacket->print();
                            std::cout << "Layer 1 info: ";
                            traPacket->print();
                            std::cout << "Layer 2 info: ";
                            netPacket->print();
                            std::cout << "Layer 3 info: ";
                            phyPacket->print();
                            std::cout << "Number of hops so far: " << hops << endl;
                        }
                    }
                }
            }
        }
    }
}

void Network::process_commands(vector<Client> &clients, vector<string> &commands, int message_limit,
                      const string &sender_port, const string &receiver_port) {
    for (int i = 0; i < commands.size(); ++i) {
        std::string command = commands[i];
        std::istringstream iss(command);
        std::vector<string> commandArgv;
        while (iss >> command) {
            commandArgv.push_back(command);
        }
        if (commandArgv[0] == "MESSAGE") {
            std::string senderId = commandArgv[1];
            std::string receiverId = commandArgv[2];
            std::string message = commands[i];
            std::string title="Command: ";
            title+=commands[i];
            std::cout<<std::string(title.size(), '-')<<endl<<title<<endl<<std::string(title.size(), '-')<<endl;
            size_t start_pos = commands[i].find('#');
            message = commands[i].substr(start_pos+1 ,commands[i].size()-start_pos-2);
            std::cout<<"Message to be sent: \""<<message<<"\"\n\n";


            std::vector<std::string> messageChunks;
            const size_t max_length = message_limit;
            size_t start = 0;
            while (start < message.length()) {
                size_t end = start + max_length;
                if (end > message.length()) {
                    end = message.length();
                }
                messageChunks.push_back(message.substr(start, end - start));
                start = end;
            }
            auto currentTimePoint = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
            std::tm* timeInfo = std::localtime(&currentTimePoint);
            char buffer[20];
            std::strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", timeInfo);
            Log log(buffer,message,messageChunks.size(),0,senderId,receiverId,true,ActivityType::MESSAGE_SENT);
            log.message_content=message;
            for (auto & client : clients) {
                if(client.client_id==senderId) {
                    client.log_entries.push_back(log);
                }
            }
            // Print the substrings
            int frameNo=1;
            for (const std::string& messageChunk : messageChunks ) {// for every frame
                std::string senderMac = findMacOfSender(senderId,clients);
                std::string nextHopMac = findMacOfNext(senderId,receiverId,clients);

                std::stack<Packet*> frame;
                auto *applicationLayerPacket = new ApplicationLayerPacket(0, senderId, receiverId, messageChunk);
                auto *transportLayerPacket = new TransportLayerPacket(1, sender_port, receiver_port);
                auto *networkLayerPacket = new NetworkLayerPacket(2, findIP(senderId, clients),findIP(receiverId, clients));
                auto * physicalLayerPacket = new PhysicalLayerPacket(3,senderMac,nextHopMac);
                physicalLayerPacket->wholeMessage = message;
                physicalLayerPacket->numberOfFrames = messageChunks.size();
                frame.push(applicationLayerPacket);
                frame.push(transportLayerPacket);
                frame.push(networkLayerPacket);
                frame.push(physicalLayerPacket);
                frame.top()->frameId=frameNo;
                std::cout<<"Frame #"<<frameNo<<"\n";
                physicalLayerPacket->print(); networkLayerPacket->print(); transportLayerPacket->print(); applicationLayerPacket->print();
                std::cout<<"Message chunk carried: \""<<messageChunk<<"\"\nNumber of hops so far: "<<frame.top()->number_of_hops<<endl;
                std::cout<<std::string(8,'-')<<endl;

                for (auto & client : clients) {
                    if(client.client_id==senderId){
                        client.outgoing_queue.push(frame);

                    }
                }
                frameNo++;
            }

        }

        else if (commandArgv[0] == "SHOW_FRAME_INFO") {
            printFrameInfo(commandArgv[2],commands[i],commandArgv,clients,sender_port,receiver_port);
        }

        else if (commandArgv[0] == "SHOW_Q_INFO") {
            std::string client2 = commandArgv[1];
            std::string inOrOut = commandArgv[2];
            std::string title="Command: ";
            title+=commands[i];
            std::cout<<std::string(title.size(), '-')<<endl<<title<<endl<<std::string(title.size(), '-')<<endl;
            int numberOfFrames;
            if(inOrOut=="out"){
                for (auto client:clients) {
                    if(client.client_id==client2){
                        numberOfFrames=client.outgoing_queue.size();
                    }
                }
                std::cout<<"Client "<<client2<<" Outgoing Queue Status\n"<<"Current total number of frames: "<<numberOfFrames<<endl;
            }
            else if(inOrOut=="in"){
                for (auto client:clients) {
                    if(client.client_id==client2){
                        numberOfFrames=client.incoming_queue.size();
                    }
                }
                std::cout<<"Client "<<client2<<" Incoming Queue Status\n"<<"Current total number of frames: "<<numberOfFrames<<endl;
            }
        }

        else if (commandArgv[0] == "SEND") {
            std::string title="Command: ";
            title+=commands[i];
            std::cout<<std::string(title.size(), '-')<<endl<<title<<endl<<std::string(title.size(), '-')<<endl;

            for(auto &client:clients){
              if(client.outgoing_queue.empty()){
                  continue;
              }
                while(!client.outgoing_queue.empty()){
                    //GET EACH PACKET IN THE FRAME
                    std::stack<Packet*> frame=client.outgoing_queue.front();
                    std::stack<Packet*> tempFrame;
                    std::stack<Packet*> tempFrameForPrint;
                    std::stack<Packet*> tempFrameForApp;
                    while(!frame.empty()){
                        tempFrame.push(frame.top());
                        tempFrameForApp.push(frame.top());// reversed frame
                        frame.pop();
                    }
                    for (int j = 0; j < 4; ++j) {
                        frame.push(tempFrame.top());
                        tempFrameForPrint.push(tempFrame.top());
                        tempFrame.pop();
                    }
                    auto phyPacket = dynamic_cast<PhysicalLayerPacket*>(frame.top());
                    auto  appPacket = dynamic_cast<ApplicationLayerPacket*>(tempFrameForApp.top());

                    // SEND DIRECTLY
                   // std::string nextHopMac = phyPacket->receiver_MAC_address;
                   phyPacket->number_of_hops++;
                    std::cout<<"Client "<<phyPacket->sender_MAC_address[0]<<" sending frame #"<<frame.top()->frameId<<" to client "<<phyPacket->receiver_MAC_address[0]<<endl;
                    while(!tempFrameForPrint.empty()){
                        tempFrameForPrint.top()->print();
                        tempFrameForPrint.pop();
                    }
                    std::cout<<"Message chunk carried: \""<<appPacket->message_data<<"\"\n"<<"Number of hops so far: "<<phyPacket->number_of_hops<<endl;
                    std::cout<<std::string(8, '-')<<endl;
                    for (auto &nextClient:clients) {
                        if(nextClient.client_mac==phyPacket->receiver_MAC_address){
                            nextClient.incoming_queue.push(frame);
                        }
                    }

                  /*  while (!frame.empty()) {
                        Packet* tempPacket = frame.top();
                        delete tempPacket;
                        frame.pop();
                    }*/
                    client.outgoing_queue.pop();
                }

            }
        }

       else if (commandArgv[0] == "RECEIVE") {
            std::string title = "Command: ";
            title += commands[i];
            std::cout << std::string(title.size(), '-') << endl << title << endl << std::string(title.size(), '-')
                      << endl;
            for(auto& recClient:clients){
                if(!recClient.incoming_queue.empty()){
                    while(!recClient.incoming_queue.empty()){
                        std::stack<Packet*> temp=recClient.incoming_queue.front();
                        std::stack<Packet*> tempForPrint=recClient.incoming_queue.front();

                        std::stack<Packet*> tempForPacket;

                        while(!temp.empty()){
                            tempForPacket.push(temp.top());
                            temp.pop();
                        }
                        auto* appPacket = dynamic_cast<ApplicationLayerPacket*>(tempForPacket.top());
                        tempForPacket.pop();
                        auto* traPacket = dynamic_cast<TransportLayerPacket*>(tempForPacket.top());
                        tempForPacket.pop();
                        auto* netPacket = dynamic_cast<NetworkLayerPacket*>(tempForPacket.top());
                        tempForPacket.pop();
                        auto* phyPacket = dynamic_cast<PhysicalLayerPacket*>(tempForPacket.top());

                        std::string nextHop = findMacOfNext(recClient.client_id,appPacket->receiver_ID,clients); // MAC of Next hop
                        if(nextHop=="drop"){
                            std::cout<<"Client "<<recClient.client_id<<" receiving frame #"<<phyPacket->frameId<< " from client "<<phyPacket->sender_MAC_address[0]<<", but intended for client "<<appPacket->receiver_ID<<". Forwarding..."<<endl;
                            std::cout<<"Error: Unreachable destination. Packets are dropped after "<<phyPacket->number_of_hops<<" hops!\n";
                            if(phyPacket->frameId==phyPacket->numberOfFrames){
                                std::cout<<"--------"<<endl;
                            }
                            if(phyPacket->frameId==1) {
                                auto currentTimePoint = std::chrono::system_clock::to_time_t(
                                        std::chrono::system_clock::now());
                                std::tm *timeInfo = std::localtime(&currentTimePoint);
                                char buffer[20];
                                std::strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", timeInfo);
                                Log log(buffer, phyPacket->wholeMessage, phyPacket->numberOfFrames, phyPacket->number_of_hops,
                                        appPacket->sender_ID, appPacket->receiver_ID, false,
                                        ActivityType::MESSAGE_DROPPED);
                                recClient.log_entries.push_back(log);
                            }
                            while (!recClient.incoming_queue.front().empty()) {
                                Packet *tempPacket = recClient.incoming_queue.front().top();
                                delete tempPacket;
                                recClient.incoming_queue.front().pop();
                            }
                        }else{
                            if(recClient.client_id!=appPacket->receiver_ID){
                                 // Forwarded
                                if(phyPacket->frameId==1){ // create log
                                    std::cout<<"Client "<<recClient.client_id<<" receiving a message from client "<<phyPacket->sender_MAC_address[0]<<", but intended for client "<<appPacket->receiver_ID<<". Forwarding..."<<endl;
                                   // phyPacket->number_of_hops++;
                                    auto currentTimePoint = std::chrono::system_clock::to_time_t(
                                            std::chrono::system_clock::now());
                                    std::tm *timeInfo = std::localtime(&currentTimePoint);
                                    char buffer[20];
                                    std::strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", timeInfo);
                                    Log log(buffer, phyPacket->wholeMessage, phyPacket->numberOfFrames, phyPacket->number_of_hops,
                                            appPacket->sender_ID, appPacket->receiver_ID, true,
                                            ActivityType::MESSAGE_FORWARDED);
                                    recClient.log_entries.push_back(log);
                                }
                                std::cout<<"Frame #"<<recClient.incoming_queue.front().top()->frameId<<" MAC address change: New sender MAC "<<recClient.client_mac<<", new receiver MAC "<<nextHop<<endl;
                                if(appPacket->message_data.find('.') != std::string::npos || appPacket->message_data.find('!') != std::string::npos || appPacket->message_data.find('?') != std::string::npos){
                                    std::cout<<std::string(8,'-')<<endl;
                                }
                                phyPacket->sender_MAC_address=recClient.client_mac;
                                phyPacket->receiver_MAC_address=nextHop;
                                recClient.outgoing_queue.push(tempForPrint);


                            }
                            else{
                                //Received
                                if(phyPacket->frameId==1){
                                    //phyPacket->number_of_hops++;
                                    auto currentTimePoint = std::chrono::system_clock::to_time_t(
                                            std::chrono::system_clock::now());
                                    std::tm *timeInfo = std::localtime(&currentTimePoint);
                                    char buffer[20];
                                    std::strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", timeInfo);
                                    Log log(buffer, phyPacket->wholeMessage, phyPacket->numberOfFrames, phyPacket->number_of_hops,
                                            appPacket->sender_ID, appPacket->receiver_ID, true,
                                            ActivityType::MESSAGE_RECEIVED);
                                    recClient.log_entries.push_back(log);
                                }
                                std::cout<<"Client "<<recClient.client_id<<" receiving frame #"<<recClient.incoming_queue.front().top()->frameId<<" from client "<<phyPacket->sender_MAC_address[0]<<", originating from client "<<appPacket->sender_ID<<endl;
                                phyPacket->print(); netPacket->print(); traPacket->print(); appPacket->print();
                                std::cout<<"Message chunk carried: \""<<appPacket->message_data<<"\"\nNumber of hops so far: "<<recClient.incoming_queue.front().top()->number_of_hops<<endl<<std::string(8,'-')<<endl;
                                if(phyPacket->frameId==phyPacket->numberOfFrames){
                                    std::cout<<"Client "<<recClient.client_id<<" received the message \""<<phyPacket->wholeMessage<<"\" from client "<<appPacket->sender_ID<<"."<<endl<<std::string(8,'-')<<endl;
                                }
                                while (!recClient.incoming_queue.front().empty()) {
                                    Packet *tempPacket = recClient.incoming_queue.front().top();
                                    delete tempPacket;
                                    recClient.incoming_queue.front().pop();
                                }
                            }

                        }

                        recClient.incoming_queue.pop();

                    }


                }
            }


        }

        else if (commandArgv[0] == "PRINT_LOG") {
            std::string message = commands[i];
            std::string title="Command: ";
            std::string logClient= commandArgv[1];
            title+=commands[i];
            std::cout<<std::string(title.size(), '-')<<endl<<title<<endl<<std::string(title.size(), '-')<<endl;
            for (auto client:clients) {
                if(!client.log_entries.empty() && client.client_id==logClient){
                    std::cout<<"Client "<<client.client_id<<" Logs:\n";
                    for (int j = 0; j < client.log_entries.size(); ++j) {
                        std::cout<<std::string(14,'-')<<endl<<"Log Entry #"<<j+1<<":"<<endl;
                        client.log_entries[j].print();
                        if(client.log_entries[j].activity_type==ActivityType::MESSAGE_RECEIVED || client.log_entries[j].activity_type==ActivityType::MESSAGE_SENT){
                            std::cout<<"Message: \""<<client.log_entries[j].message_content<<"\"\n";
                        }
                    }
                }

            }
        }

        else{
            std::string message = commands[i];
            std::string title="Command: ";
            title+=commands[i];
            std::cout<<std::string(title.size(), '-')<<endl<<title<<endl<<std::string(title.size(), '-')<<endl<<"Invalid command.\n";}//invalid command

    }



}

vector<Client> Network::read_clients(const string &filename) {
    vector<Client> clients;
    std::ifstream file(filename);
    if (!file.is_open()) {
        std::cout << "File Cannot Open" << filename << std::endl;
    }
    std::string line;
    while (getline(file, line)) {
        std::istringstream iss(line);
        std::vector<std::string> words;
        if(std::isdigit(line[0])){
            numberOfClients = line[0];
        }
        else{
            // Split the line into words based on space characters
            while (iss >> line) {
                words.push_back(line);
            }
            Client client(words[0],words[1],words[2]);
            clients.push_back(client);
            words.clear();
        }
    }
    file.close();
    // TODO: Read clients from the given input file and return a vector of Client instances.
    return clients;
}

void Network::read_routing_tables(vector<Client> &clients, const string &filename) {
    std::ifstream file(filename);
    if (!file.is_open()) {
        std::cout << "File Cannot Open" << filename << std::endl;
    }
// Burayi bi degistir , unordered map tanimlamadan yap.
    std::string line;
    std::unordered_map<std::string, string> routingMap;
    int j =0; // indicates that which client we are in
    while (getline(file, line)) {
        if(line[0] == '-'){ // skip to next client
            clients[j].routing_table=routingMap;
            routingMap.clear();
            j++;
        }
        else{
            routingMap[line.substr(0, 1)] = line.substr(2, 1);
        }
    }
    file.close();
    // TODO: Read the routing tables from the given input file and populate the clients' routing_table member variable.
}

// Returns a list of token lists for each command
vector<string> Network::read_commands(const string &filename) {
    vector<string> commands;
    std::ifstream file(filename);
    if (!file.is_open()) {
        std::cout << "File Cannot Open" << filename << std::endl;
    }
    std::string line;
    while (getline(file, line)) {
        if(!std::isdigit(line[0])){
            commands.push_back(line);
        }
    }
    file.close();
    // TODO: Read commands from the given input file and return them as a vector of strings.
    return commands;
}

Network::~Network() {

}
// TODO: Execute the commands given as a vector of strings while utilizing the remaining arguments.
/* Don't use any static variables, assume this method will be called over and over during testing.
 Don't forget to update the necessary member variables after processing each command. For example,
 after the MESSAGE command, the outgoing queue of the sender must have the expected frames ready to send. */
