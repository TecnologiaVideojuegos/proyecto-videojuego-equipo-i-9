% PATH = GETPATH(PP, SX, SY)
%
% Find and return the shortest path from (SX, SY) to a goal.  Uses a
% pathplan object PP, created by PATHPLAN, for information about goal
% locations and edge costs.  Result is a k * 2 array of waypoints, one per
% row, with each waypoint given as (X, Y).

function path = getpath(pp, sx, sy)

% unpack the pathplan object
planner = pp{1};
mdp = pp{2};

% get the path, and prune it to remove unnecessary states
state = mdp.stateIndex(sx-1, sy-1);
pathidx = planner.getPath(state);
pathidx = planner.prunePath(pathidx);

% get rid of excess entries in the path
sentinel = find((pathidx == -1), 1);
if (isempty(sentinel))
    pathlen = size(pathidx,1);
else
    pathlen = sentinel - 1;
end

% convert to XY coordinates
path = zeros(pathlen, 2);
for i = 1:pathlen
    path(i,1) = mdp.getX(pathidx(i))+1;
    path(i,2) = mdp.getY(pathidx(i))+1;
end

