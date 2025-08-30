
using Microsoft.EntityFrameworkCore;

namespace MathGame_API
{
    public class MathGameContext : DbContext
    {
        public MathGameContext(DbContextOptions<MathGameContext> options)
            : base(options)
        {
        }

        public DbSet<Leaderboard> Leaderboard { get; set; }
        public DbSet<LevelSummary> LevelSummaries { get; set; }
    }
}
