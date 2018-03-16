% LEN = PATHLEN(XS, YS, PP)
% LEN = PATHLEN(XS, YS, PP, COSTS)
%
% Compute path lengths.  (XS, YS) are the coordinates of the vertices of
% the path; PP is as returned by SHORTESTPATHS; COSTS is an array of state
% costs.  If COSTS is [] or not provided, we default to the state costs
% stored in PP.

function len = pathlen(xs, ys, pp, costs)

if (nargin < 4)
    costs = [];
end
ct = costs';

mdp = pp{2};

idx = zeros(size(xs));
for i = 1:length(xs)
    idx(i) = mdp.stateIndex(xs(i)-1, ys(i)-1);
end

len = 0;
for i = 1:length(idx)-1
    if (~isempty(costs))
        len = len + mdp.getLineCost(idx(i), idx(i+1), ct(:));
    else
        len = len + mdp.getLineCost(idx(i), idx(i+1));
    end
end

