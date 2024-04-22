//
// Created by alperen on 2.10.2023.
//

#include "Log.h"

Log::Log(const string &_timestamp, const string &_message, int _number_of_frames, int _number_of_hops, const string &_sender_id,
         const string &_receiver_id, bool _success, ActivityType _type) {
    timestamp = _timestamp;
    message_content = _message;
    number_of_frames = _number_of_frames;
    number_of_hops = _number_of_hops;
    sender_id = _sender_id;
    receiver_id = _receiver_id;
    success_status = _success;
    activity_type = _type;
}
std::string Log::activityTypeToString(ActivityType type) {
    switch (type) {
        case ActivityType::MESSAGE_RECEIVED:
            return "Message Received";
        case ActivityType::MESSAGE_FORWARDED:
            return "Message Forwarded";
        case ActivityType::MESSAGE_SENT:
            return "Message Sent";
        case ActivityType::MESSAGE_DROPPED:
            return "Message Dropped";
        default:
            return "Unknown ActivityType";
    }
}
void Log::print(){
    std::string success;
    if(success_status){
        success="Yes";
    }else{success="No";}
    std::cout << "Activity: " << activityTypeToString(activity_type)<<"\nTimestamp: "<<timestamp<<"\nNumber of frames: "<<number_of_frames;
    std::cout<<"\nNumber of hops: "<<number_of_hops<<"\nSender ID: "<<sender_id<<"\nReceiver ID: "<<receiver_id<<"\nSuccess: "<<success<<endl;

}

Log::~Log() {
    // TODO: Free any dynamically allocated memory if necessary.
}