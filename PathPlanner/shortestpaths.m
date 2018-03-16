% SHORTESTPATHS
%
% Single-destination shortest paths.  
%
% [value, pp] = shortestpaths(cost, gx, gy)
% [value, pp] = shortestpaths(cost, gx, gy, gcost)
%
% Given an array COST of cell costs, find the shortest paths from
% everywhere to (GX, GY).  (If GX and GY are vectors, they represent
% multiple goals.)  If GCOST is given, it is the cost for using each goal.
%
% Returns the total costs of all paths in an array the same shape as
% COST.  Also returns a pathplan object in PP (which can later be used to
% find optimal paths using GETPATH).
%
% A longer example of how to call is given at the end of this file.

function [value, ppobj] = shortestpaths(cost, gx, gy, gcost)

% import Java classes
import PathPlan.PathPlanner;
import PathPlan.MDP;

% default arguments
if (nargin < 4)
    gcost = zeros(size(gx));
end

% build an MDP with the given cost map
[h, w] = size(cost);
mdp = PathPlan.MDP(w, h, 0);
ct = cost';
mdp.setCosts(ct(:), 0);

% call the path planner to compute path length for each state
pp = PathPlan.PathPlanner;
pp.init(mdp);
for i = 1:length(gx)
    pp.addGoal(mdp.stateIndex(gx(i)-1, gy(i)-1), gcost(i));
end
pp.plan;

% retrieve the value and make it the right shape
value = reshape(pp.getTotalCosts,[w h])';

ppobj = {pp mdp};

return



%
% An example of how to call: mostly random costs (low-pass filtered),
% but with a square of higher costs near the middle.
%

% build a cost map
cost = rand(200,300);
gauss = exp(-(-3:.5:3).^2);
cost = conv2(gauss, gauss, cost, 'valid');
cost = conv2(gauss, gauss, cost, 'valid');
cost = cost - min(cost(:));
cost = cost ./ max(cost(:));
cost(100:150,150:200) = cost(100:150,150:200) + 2;

% pick a goal state
gx = 1+floor(rand*size(cost,2));
gy = 1+floor(rand*size(cost,1));

% call the path planner
tc = shortestpaths(cost, gx, gy);
imagesc(tc); axis image

