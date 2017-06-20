library Project3;

uses
   SysUtils, Classes, Math;
 
function AddFunction(var Width,Height:Integer):Integer;stdcall;
begin
    Result:=Width+Height;
end;

exports AddFunction;

begin
end.
 