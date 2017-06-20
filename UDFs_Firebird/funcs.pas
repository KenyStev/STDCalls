unit funcs;

interface
    function AddFunction(var Width,Height:Integer):Integer;stdcall;
implementation
    function AddFunction(var Width,Height:Integer):Integer;
    begin
        Result:=Width+Height;
    end;
end.