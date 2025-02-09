import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { DotFilledIcon, DotsVerticalIcon } from "@radix-ui/react-icons";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { deleteProject } from "@/redux/Project/Project.Action";
import { ArrowLeftIcon, TrashIcon } from "@radix-ui/react-icons";

const ProjectCard = ({ item }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [open, setOpen] = useState(false);

  const handleDeleteProject = () => {
    dispatch(deleteProject({ projectId: item.id }));
    setOpen(false);
  };

  return (
    <Card className="p-5 w-full lg:max-w-3xl">
      <div className="space-y-5">
        <div className="space-y-2">
          <div className="flex justify-between">
            <div className="flex items-center gap-5">
              <h1
                onClick={() => navigate(`/project/${item.id}`)}
                className="cursor-pointer font-bold text-lg"
              >
                {item.name}
              </h1>
              <DotFilledIcon />
              <p className="text-sm text-gray-400">{item.category}</p>
            </div>
            <div>
              <DropdownMenu>
                <DropdownMenuTrigger>
                  <Button className="rounded-full" variant="ghost" size="icon">
                    <DotsVerticalIcon />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuItem
                    onClick={() => navigate(`/project/update/${item.id}`)}
                  >
                    Update
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={() => setOpen(true)}>Delete</DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>

          <p className="text-gray-500 text-sm">{item.description}</p>
        </div>

        <div className="flex flex-wrap gap-2 items-center">
          {item.tags.map((tag) => (
            <Badge key={tag} variant="outline">
              {tag}
            </Badge>
          ))}
        </div>
      </div>

      {/* Confirmation Dialog */}
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Confirm Deletion</DialogTitle>
          </DialogHeader>
          <p>Are you sure you want to delete this project?</p>
          <DialogFooter>
            <Button variant="outline" onClick={() => setOpen(false)}>
              <ArrowLeftIcon className="w-5 h-5" /> Go Back
            </Button>
            <Button variant="outline" onClick={handleDeleteProject}>
              <TrashIcon className="w-5 h-5" /> Delete
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </Card>
  );
};

export default ProjectCard;
