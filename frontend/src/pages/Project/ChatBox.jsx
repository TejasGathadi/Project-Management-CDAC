import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import {
  fetchChatByProject,
  fetchChatMessages,
  messageRecived,
  sendMessage,
} from "@/redux/Chat/Action";
import { PaperPlaneIcon } from "@radix-ui/react-icons";
import { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";

const ChatBox = () => {
  const [message, setMessage] = useState("");
  const dispatch = useDispatch();
  const { id } = useParams();
  const { chat, auth } = useSelector((store) => store);
  const chatContainerRef = useRef(null);

  useEffect(() => {
    dispatch(fetchChatByProject(id));
  }, []);

  useEffect(() => {
    if (chat.chat) {
      dispatch(fetchChatMessages(chat.chat?.id));
    }
  }, [chat.chat]);

  const handleMessageChange = (e) => setMessage(e.target.value);

  const handleSendMessage = () => {
    if (message.trim() === "") return; // Prevent sending empty messages
    dispatch(
      sendMessage({
        message: {
          senderId: auth.user?.id,
          projectId: id,
          content: message,
        },
        sendToServer: sendMessageToServer,
      })
    );
    setMessage("");
  };

  // Send message on pressing "Enter"
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleSendMessage();
    }
  };

  const sendMessageToServer = (message) => {
    console.log(message);
  };

  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [chat.messages]);

  return (
    <div className="sticky">
      <div className="border rounded-lg">
        <h1 className="border-b p-5 text-black">Chat Box</h1> {/* Ensuring header text is black */}
        <ScrollArea className="h-[32rem] w-full p-5 flex gap-3 flex-col">
          {chat.messages?.map((item, i) =>
            item.sender.id === auth.user.id ? (
              <div ref={chatContainerRef} key={i} className="flex gap-2 mb-2">
                <Avatar>
                  <AvatarFallback className="text-black">{item.sender.fullName[0]}</AvatarFallback>
                </Avatar>
                <div className="space-y-2 py-2 px-5 border rounded-ss-2xl rounded-e-xl">
                  <p className="text-black">{item.sender?.fullName}</p>
                  <p className="text-black">{item.content}</p> {/* Message text is black */}
                </div>
              </div>
            ) : (
              <div ref={chatContainerRef} key={i} className="flex mb-2 gap-2 justify-end">
                <div className="space-y-2 py-2 px-5 border rounded-se-2xl rounded-s-xl">
                  <p className="text-black">{item.sender?.fullName}</p>
                  <p className="text-black">{item.content}</p> {/* Message text is black */}
                </div>
                <Avatar>
                  <AvatarFallback className="text-black">{item.sender.fullName[0]}</AvatarFallback>
                </Avatar>
              </div>
            )
          )}
        </ScrollArea>
        <div className="relative p-0">
          <Input
            value={message}
            onChange={handleMessageChange}
            onKeyDown={handleKeyDown} // Listening for Enter key press
            placeholder="Type a message..."
            className="py-7 border-t outline-none focus:outline-none focus:ring-0 rounded-none border-b-0 border-x-0 text-black" // Ensuring input text is black
          />
          <Button
            onClick={handleSendMessage}
            className="absolute right-2 top-3 rounded-full"
            size="icon"
            variant="ghost"
          >
            <PaperPlaneIcon />
          </Button>
        </div>
      </div>
    </div>
  );
};

export default ChatBox;
