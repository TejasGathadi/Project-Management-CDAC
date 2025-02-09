import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useDispatch } from "react-redux";
import { inviteToProject } from "@/redux/Project/Project.Action";
import { useState } from "react";

const formSchema = z.object({
  email: z.string().email("Invalid email address"),
});

const InviteUserForm = ({ projectId }) => {
  const dispatch = useDispatch();
  const [showSuccessDialog, setShowSuccessDialog] = useState(false);
  
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
    },
  });

  const onSubmit = (data) => {
    data.projectId = projectId;
    dispatch(inviteToProject(data));
    console.log("Sent invitation:", data);

    // Show success message
    setShowSuccessDialog(true);
  };

  const handleOk = () => {
    setShowSuccessDialog(false);
    window.location.reload(); // Refresh page
  };

  return (
    <div>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    {...field}
                    className="border w-full border-gray-700 py-5 px-5"
                    placeholder="Enter user email"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <Button type="submit" className="w-full bg-green-500 py-5">
            SEND INVITATION
          </Button>
        </form>
      </Form>

      {/* Success Dialog */}
      {showSuccessDialog && (
        <Dialog open={showSuccessDialog} onOpenChange={setShowSuccessDialog}>
          <DialogContent className="text-center">
            <DialogHeader>
              <DialogTitle className="text-green-600 text-2xl">✔️ Invitation Sent!</DialogTitle>
            </DialogHeader>
            <p className="text-gray-600">The invitation was sent successfully.</p>
            <DialogFooter>
              <Button onClick={handleOk} className="bg-blue-500 text-white">
                OK
              </Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  );
};

export default InviteUserForm;
